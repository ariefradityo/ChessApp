package com.bukalapak.test.chessapp.model;

import com.bukalapak.test.chessapp.util.Util;

/**
 * Created by arief.radityo@gmail.com on 10/03/2016 in ChessApp.
 *
 * Chess Piece model
 */
public class ChessPiece {

    private PieceCode pieceCode;

    private int prevRow;
    private int prevCol;

    public ChessPiece(PieceCode pieceCode, char prevRow, char prevCol){
        this.pieceCode = pieceCode;
        this.prevRow = Util.normalizeRow(prevRow);
        this.prevCol = Util.normalizeCol(prevCol);
    }

    public PieceCode getPieceCode() {
        return pieceCode;
    }

    public void setPieceCode(PieceCode pieceCode) {
        this.pieceCode = pieceCode;
    }

    public int getPrevRow() {
        return prevRow;
    }

    public void setPrevRow(char prevRow){
        this.prevRow = Util.normalizeRow(prevRow);
    }

    public void setPrevRow(int prevRow) {
        this.prevRow = prevRow;
    }

    public void setPrevCol(char prevCol){
        this.prevCol = Util.normalizeCol(prevCol);
    }

    public int getPrevCol() {
        return prevCol;
    }

    public void setPrevCol(int prevCol) {
        this.prevCol = prevCol;
    }

    public int getOldIndex(){
        return 8*(prevRow) + prevCol;
    }
}
