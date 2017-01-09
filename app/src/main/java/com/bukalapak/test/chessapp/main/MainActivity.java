package com.bukalapak.test.chessapp.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.bukalapak.test.chessapp.R;
import com.bukalapak.test.chessapp.model.ChessPiece;
import com.bukalapak.test.chessapp.model.PieceCode;
import com.bukalapak.test.chessapp.model.Tile;
import com.bukalapak.test.chessapp.model.TileType;
import com.bukalapak.test.chessapp.service.SocketService;
import com.bukalapak.test.chessapp.service.SocketService_;
import com.bukalapak.test.chessapp.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@OptionsMenu(R.menu.main_menu)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String DATA_DELIMITER = " ";

    @ViewById(R.id.mGVchessBoard)
    protected GridView mGVchessBoard;

    private ChessBoardAdapter chessBoardAdapter;
    private SocketService_.IntentBuilder_ socketServiceIntent;
    private Map<PieceCode, ChessPiece> chessPieces;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
    }

    @AfterViews
    protected void init() {
        this.chessPieces = new TreeMap<>();
        this.chessBoardAdapter = new ChessBoardAdapter(this, new ArrayList<Tile>());
        this.mGVchessBoard.setAdapter(chessBoardAdapter);

        //EXECUTE ASYNC TASK TO GENERATE BOARD
        generateBoard(chessBoardAdapter.getTiles());

        //SHOW LOADING DIALOG
        progressDialog = ProgressDialog.show(this, null,
                "Mohon tunggu sebentar", true);
        progressDialog.setCancelable(false);

        this.socketServiceIntent = SocketService_.intent(this);
        connectToServer();
    }

    /**
     * START SOCKET SERVICE AND CONNECT TO SERVER
     */
    private void connectToServer(){
        if (!Util.isSocketServiceRunning(this)) {
            socketServiceIntent.persistentConnection().start();
        }
    }

    /**
     * REFRESH AND RECONNECT TO SERVER
     */
    @OptionsItem(R.id.menu_refresh)
    protected void onRefreshClicked(){
        progressDialog.show();
        connectToServer();
    }

    /**
     * Success message receiver
     * Receiver only registered when main activity is visible
     *
     * @param message
     */
    @Receiver(actions = SocketService.ACTION_SUCCESS, local = true, registerAt = Receiver.RegisterAt.OnStartOnStop)
    protected void onSuccessMessageReceived(@Receiver.Extra(SocketService.MESSAGE_KEY) String message) {
        Log.d(TAG, "Chess pieces: " + message);
        populateBoardPieces(message);
        dismissDialog();
    }

    /**
     * Error message receiver
     * Receiver only registered when main activity is visible
     *
     * @param message
     */
    @Receiver(actions = SocketService.ACTION_ERROR, local = true, registerAt = Receiver.RegisterAt.OnStartOnStop)
    protected void onErrorMessageReceived(@Receiver.Extra(SocketService.MESSAGE_KEY) String message) {
        dismissDialog();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * GENEREATE CLEAN BOARD
     *
     * @param tiles
     */
    @Background
    protected void generateBoard(List<Tile> tiles) {
        createCleanBoard(tiles, 0, true);
        updateBoardVisual();
    }

    /**
     * POPULATE BOARD WITH CHESS PIECES OR UPDATE THEM
     *
     * @param payload
     */
    @Background
    protected void populateBoardPieces(String payload) {
        String[] dataArray = payload.split(DATA_DELIMITER);
        for (String data : dataArray) {
            PieceCode pieceCode = PieceCode.valueOf(String.valueOf(data.charAt(0)));
            if (!chessPieces.containsKey(pieceCode)) {
                chessPieces.put(pieceCode, new ChessPiece(pieceCode, data.charAt(2), data.charAt(1)));
            }
            chessBoardAdapter.movePiece(chessPieces.get(pieceCode), data.charAt(2), data.charAt(1));
        }
        updateBoardVisual();
    }

    /**
     * Update board visuals
     */
    @UiThread
    protected void updateBoardVisual() {
        chessBoardAdapter.notifyDataSetChanged();
    }

    /**
     * Recursive method to create board
     *
     * @param tiles
     * @param count
     * @param even
     */
    private void createCleanBoard(List<Tile> tiles, int count, boolean even) {
        if (count == 64) {
            return;
        }
        //CHANGE ODD OR EVEN ROW
        if (count % 8 == 0 && count != 0) {
            even = !even;
        }

        //CHECK ODD OR EVEN COLUMN
        if (count % 2 == 0) {
            if (even) {
                tiles.add(new Tile(TileType.WHITE_TILE));
                createCleanBoard(tiles, count + 1, even);
                return;
            }
            tiles.add(new Tile(TileType.GREY_TILE));
            createCleanBoard(tiles, count + 1, even);
            return;
        }
        if (even) {
            tiles.add(new Tile(TileType.GREY_TILE));
            createCleanBoard(tiles, count + 1, even);
            return;
        }
        tiles.add(new Tile(TileType.WHITE_TILE));
        createCleanBoard(tiles, count + 1, even);
    }

    private void dismissDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
