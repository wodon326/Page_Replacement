import java.util.*;

public class PageReplacement {
    public ArrayList<PageResult> FIFO(String Reference_String,int frame_num){
        ArrayList<PageResult> Result = new ArrayList<>();
        ArrayList<Character> now_frame = new ArrayList<>();
        Queue<Character> queue = new LinkedList<>();
        boolean isPageFault = true;
        for (int i = 0; i < Reference_String.length(); i++) {
            char target = Reference_String.charAt(i);
            isPageFault = true;
            for(char c : now_frame){
                if(target == c) {
                    Result.add(new PageResult(target,false,new ArrayList<>(now_frame)));
                    isPageFault = false;
                    break;
                }
            }
            if(isPageFault){
                if(now_frame.size()<frame_num){
                    now_frame.add(target);
                    queue.add(target);
                    Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                }
                else{
                    char queue_char = queue.poll();
                    for(int index = 0;index<now_frame.size();index++){
                        if(queue_char==now_frame.get(index)){
                            now_frame.set(index,target);
                            queue.add(target);
                            Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                        }
                    }
                }
            }
        }
        return Result;
    }
    public ArrayList<PageResult> Optimal(String Reference_String,int frame_num){
        ArrayList<PageResult> Result = new ArrayList<>();
        ArrayList<Character> now_frame = new ArrayList<>();
        boolean isPageFault = true;
        for (int i = 0; i < Reference_String.length(); i++) {
            char target = Reference_String.charAt(i);
            isPageFault = true;
            for(char c : now_frame){
                if(target == c) {
                    Result.add(new PageResult(target,false,new ArrayList<>(now_frame)));
                    isPageFault = false;
                    break;
                }
            }
            if(isPageFault){
                if(now_frame.size()<frame_num){
                    now_frame.add(target);
                    Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                }
                else{
                    char longest_page = '0';
                    int page_index;
                    int max=0;
                    for(char c : now_frame){
                        page_index = 0;
                        for (int index = i+1; index < Reference_String.length(); index++) {
                            if(Reference_String.charAt(index)==c){
                                page_index = index;
                                break;
                            }
                        }
                        if(page_index==0)
                            page_index = Reference_String.length();

                        if(max<page_index){
                            max = page_index;
                            longest_page = c;
                        }
                    }
                    for(int index = 0;index<now_frame.size();index++){
                        if(longest_page==now_frame.get(index)){
                            now_frame.set(index,target);
                            Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                            break;
                        }
                    }
                }
            }
        }
        return Result;
    }
    public ArrayList<PageResult> LRU(String Reference_String,int frame_num){
        ArrayList<PageResult> Result = new ArrayList<>();
        ArrayList<Character> now_frame = new ArrayList<>();
        Deque<Character> deque = new LinkedList<>();
        boolean isPageFault = true;
        for (int i = 0; i < Reference_String.length(); i++) {
            char target = Reference_String.charAt(i);
            isPageFault = true;
            for(char c : now_frame){
                if(target == c) {
                    for(char k : deque){
                        System.out.print(k);
                    }
                    System.out.println();
                    deque.remove(target);
                    deque.addLast(target);
                    for(char k : deque){
                        System.out.print(k);
                    }
                    System.out.println();
                    Result.add(new PageResult(target,false,new ArrayList<>(now_frame)));
                    isPageFault = false;
                    break;
                }
            }
            if(isPageFault){
                if(now_frame.size()<frame_num){
                    now_frame.add(target);
                    deque.addLast(target);
                    Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                }
                else{
                    char deque_char = deque.poll();
                    for(int index = 0;index<now_frame.size();index++){
                        if(deque_char==now_frame.get(index)){
                            now_frame.set(index,target);
                            deque.addLast(target);
                            Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                        }
                    }
                }
            }
        }
        return Result;
    }
    public ArrayList<PageResult> Second_Chance(String Reference_String,int frame_num){
        ArrayList<PageResult> Result = new ArrayList<>();
        ArrayList<Character> now_frame = new ArrayList<>();
        Queue<Character> queue = new LinkedList<>();
        HashSet<Character> reference_hashset = new HashSet<>();
        boolean isPageFault = true;
        for (int i = 0; i < Reference_String.length(); i++) {
            char target = Reference_String.charAt(i);
            isPageFault = true;
            for(char c : now_frame){
                if(target == c) {
                    reference_hashset.add(target);
                    Result.add(new PageResult(target,false,new ArrayList<>(now_frame)));
                    isPageFault = false;
                    break;
                }
            }
            if(isPageFault){
                if(now_frame.size()<frame_num){
                    now_frame.add(target);
                    queue.add(target);
                    Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                }
                else{
                    char queue_char;
                    while(true){
                        queue_char = queue.poll();
                        if(reference_hashset.contains(queue_char)){
                            queue.add(queue_char);
                            reference_hashset.remove(queue_char);
                        }
                        else break;
                    }
                    for(int index = 0;index<now_frame.size();index++){
                        if(queue_char==now_frame.get(index)){
                            now_frame.set(index,target);
                            queue.add(target);
                            Result.add(new PageResult(target,true,new ArrayList<>(now_frame)));
                        }
                    }
                }
            }
        }
        return Result;
    }

}
