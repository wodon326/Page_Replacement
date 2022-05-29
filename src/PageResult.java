import java.util.ArrayList;

public class PageResult {
    private char Reference_Char;
    private boolean isPageFault;
    private ArrayList<Character> result;
    public PageResult(char Reference_Char,boolean isPageFault,ArrayList<Character> result){
        this.isPageFault = isPageFault;
        this.Reference_Char = Reference_Char;
        this.result = result;
    }
    public ArrayList<Character> getResult() {
        return result;
    }

    public boolean isPageFault() {
        return isPageFault;
    }

    public char getReference_Char() {
        return Reference_Char;
    }
}
