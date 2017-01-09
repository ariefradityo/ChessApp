package com.bukalapak.test.chessapp.model;

/**
 * Created by arief.radityo@gmail.com on 10/03/2016 in ChessApp.
 *
 * Model of a chessboard tile
 */
public class Tile {

    private ChessPiece chessPiece;
    private TileType tileType;

    public Tile(TileType type){
        this.tileType = type;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public TileType getTileType() {
        return tileType;
    }


}
