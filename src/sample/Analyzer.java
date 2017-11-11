package sample;

public class Analyzer {

    //<Выражение>
    static public int Expression(String str, int curPos){

        char curChar = str.charAt(curPos);

        if(isRussianSymbol(curChar) || isPunctuation(curChar)){
            curPos = Expression(str, ++curPos);
        }
        else if(isFLOQuote(curChar)){
            curPos = FLExpression(str, ++curPos);
            isFLCQuote(str.charAt(curPos), curPos);
            curPos = Expression(str, ++curPos);
        }
        else if(curChar!='\n')
            throw new GrammarException("Выражение неверно: ошибка обнаружена на "+ (curPos+1)+" символе", curPos);

        return curPos;
    }

    //<Выражение первого порядка>
    private static int FLExpression(String str, Integer curPos){

        char curChar = str.charAt(curPos);

        if(curChar == '\n')
            return curPos - 1;
        else if(isRussianSymbol(curChar) || isPunctuation(curChar)){
            curPos = FLExpression(str, ++curPos);
        }
        else if(isSLOQuote(curChar)){
            curPos = SLExpression(str, ++curPos);
            isSLCQuote(str.charAt(curPos), curPos);
            curPos = FLExpression(str, ++curPos);
        }

        //Не вошли ни в одно из условий => пустой символ

        return curPos;
    }


    //Выражение второго порядка
    private static int SLExpression(String str, Integer curPos){

        char curChar = str.charAt(curPos);

        if(curChar == '\n')
            return curPos - 1;
        else if(isRussianSymbol(curChar) || isPunctuation(curChar)){
            curPos = SLExpression(str, ++curPos);
        }
        else if(isFLOQuote(curChar)) {
            curPos = FLExpression(str, ++curPos);
            isFLCQuote(str.charAt(curPos), curPos);
            curPos = SLExpression(str, ++curPos);
        }

        //Не вошли ни в одно из условий => пустой символ

        return curPos;
    }


    //<Открывающая "ёлочка">
    private static boolean isFLOQuote(char ch){
        return ch == '«';
    }


    //<Закрывающая "ёлочка">
    private static void isFLCQuote(char ch, int curPos) {
        if (ch != '»')
            throw new GrammarException("Выражение неверно: ошибка обнаружена на "+ (curPos+1)+"-ом символе", curPos);
    }

    //<Открывающая компьютерная кавычка>
    private static boolean isSLOQuote(char ch) {
        return ch == '\"';
    }


    //<Закрывающая компьютерная кавычка>
    private static void isSLCQuote(char ch, int curPos){
        if(ch != '\"')
            throw new GrammarException("Выражение неверно: ошибка обнаружена на "+ (curPos+1)+"-ом символе", curPos);
    }

    //<Буква>
    private static boolean isRussianSymbol(char ch){
        return ch>='А'&&ch<='Я' || ch>='а'&&ch<='я' || ch== 'ё' ||ch == 'Ё';
    }

    //<Знак пунктуации>
    private static boolean isPunctuation(char ch){
        return ch == ' ' || ch == '.' || ch == ',' || ch == '?' || ch == '!' || ch == '-';
    }
}
