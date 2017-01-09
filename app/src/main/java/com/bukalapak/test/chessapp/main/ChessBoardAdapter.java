package com.bukalapak.test.chessapp.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bukalapak.test.chessapp.R;
import com.bukalapak.test.chessapp.model.ChessPiece;
import com.bukalapak.test.chessapp.model.Tile;
import com.bukalapak.test.chessapp.model.TileType;
import com.bukalapak.test.chessapp.util.Util;

import java.util.List;

/**
 * Created by arief.radityo@gmail.com on 10/03/2016 in ChessApp.
 */
public class ChessBoardAdapter extends BaseAdapter {

    private List<Tile> tiles;
    private Context context;

    public ChessBoardAdapter(Context context, List<Tile> tiles) {
        this.context = context;
        this.tiles = tiles;
    }

    @Override
    public int getCount() {
        return tiles.size();
    }

    @Override
    public int getViewTypeCount() {
        return TileType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return tiles.get(position).getTileType().ordinal();
    }

    @Override
    public Object getItem(int position) {
        return tiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ChessPiece chessPiece = tiles.get(position).getChessPiece();

        ViewHolder viewHolder;
        if (view == null) {
            if (getItemViewType(position) == TileType.WHITE_TILE.ordinal())
                view = LayoutInflater.from(context).inflate(R.layout.white_tile, parent, false);
            else
                view = LayoutInflater.from(context).inflate(R.layout.grey_tile, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        setPieceImage(viewHolder, chessPiece);

        return view;
    }

    public void movePiece(ChessPiece chessPiece, char row, char col){
        //REMOVE OLD CHESS PIECE
        tiles.get(chessPiece.getOldIndex()).setChessPiece(null);
        //MOVE NEW CHESS PIECE
        chessPiece.setPrevRow(row);
        chessPiece.setPrevCol(col);
        tiles.get(rowColumnToIndex(row, col)).setChessPiece(chessPiece);
    }

    private int rowColumnToIndex(char row, char col){
        return 8* Util.normalizeRow(row) + Util.normalizeCol(col);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * SET CORRESPONDING IMAGE
     * @param viewHolder
     * @param chessPiece
     */
    private void setPieceImage(ViewHolder viewHolder, ChessPiece chessPiece){
        if(chessPiece != null){
            switch(chessPiece.getPieceCode()){
                case K:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_black_king);
                    break;
                case Q:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_black_queen);
                    break;
                case B:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_black_bishop);
                    break;
                case N:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_black_horse);
                    break;
                case R:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_black_rook);
                    break;
                case k:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_white_king);
                    break;
                case q:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_white_queen);
                    break;
                case b:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_white_bishop);
                    break;
                case n:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_white_horse);
                    break;
                case r:
                    viewHolder.mIVpieceImage.setImageResource(R.drawable.ic_white_rook);
                    break;
            }
        }
        else {
            viewHolder.mIVpieceImage.setImageResource(android.R.color.transparent);
        }
    }

    private static class ViewHolder {
        //TODO: Implement viewholder

        ImageView mIVpieceImage;

        public ViewHolder(View view) {
            mIVpieceImage = (ImageView) view.findViewById(R.id.mIVpieceImage);
        }
    }
}
