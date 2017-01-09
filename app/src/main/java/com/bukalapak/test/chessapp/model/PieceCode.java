package com.bukalapak.test.chessapp.model;

/**
 * Created by arief.radityo@gmail.com on 10/03/2016 in ChessApp.
 */
public enum PieceCode {

    K("White King"),
    Q("White Queen"),
    B("White Bishop"),
    N("White Knight"),
    R("White Rook"),
    k("Black King"),
    q("Black Queen"),
    b("Black Bishop"),
    n("Black Knight"),
    r("Black Rook");

    private String value;

    PieceCode(String value) {
        this.value = value;
    }

    String showName(){
        return value;
    }
}
