package sample;

public class GrammarException extends RuntimeException{
    private int symbolPosiion;

    GrammarException(String message, int symbolPosiion){
        super(message);
        this.symbolPosiion = symbolPosiion;
    }

    public int getSymbolPosiion() {
        return symbolPosiion;
    }
}
