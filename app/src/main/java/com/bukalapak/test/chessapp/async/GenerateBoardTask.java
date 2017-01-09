package com.bukalapak.test.chessapp.async;

import android.os.AsyncTask;

import com.bukalapak.test.chessapp.main.ChessBoardAdapter;
import com.bukalapak.test.chessapp.model.Tile;
import com.bukalapak.test.chessapp.model.TileType;

import java.util.List;

/**
 * Created by arief.radityo@gmail.com on 10/03/2016 in ChessApp.
 *
 * AsyncTask to connect to server
 *
 * Please use {@link com.bukalapak.test.chessapp.service.SocketService}
 */
@Deprecated
public class GenerateBoardTask extends AsyncTask<Void, Void, Void> {

    private ChessBoardAdapter chessBoardAdapter;
    private List<Tile> tiles;

    public GenerateBoardTask(ChessBoardAdapter adapter) {
        this.chessBoardAdapter = adapter;
        tiles = chessBoardAdapter.getTiles();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //IF NO TILES THEN GENERATE NEW BOARD
        if (tiles.size() == 0) {
            generateCleanBoard(0, true);
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //SHOW CHANGES
        chessBoardAdapter.notifyDataSetChanged();
    }

    private void generateCleanBoard(int count, boolean even) {
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
                generateCleanBoard(count + 1, even);
                return;
            }
            tiles.add(new Tile(TileType.GREY_TILE));
            generateCleanBoard(count + 1, even);
            return;
        }
        if (even) {
            tiles.add(new Tile(TileType.GREY_TILE));
            generateCleanBoard(count + 1, even);
            return;
        }
        tiles.add(new Tile(TileType.WHITE_TILE));
        generateCleanBoard(count + 1, even);
    }
}
