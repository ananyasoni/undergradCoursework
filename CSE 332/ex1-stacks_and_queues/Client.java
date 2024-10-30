public class Client {

    public static void test(MyStack<String> stk){
        //add first item
        stk.push("Hello World!");
        if(stk.size() == 1 && stk.peek().equals("Hello World!")){
            System.out.println("Successfully added one item!");
        }
        else{
            System.out.println("First item not added correctly");
        }
        //remove that item
        String s = stk.pop();
        if(stk.isEmpty() && stk.size() == 0 && s.equals("Hello World!")){
            System.out.println("Successfully removed that one item!");
        } else{
            System.out.println("Incorrectly removed the first item.");
        }

        for(int i = 0; i < 1000; i++){
            stk.push(""+i);
        }
        if(stk.size() == 1000){
            System.out.println("Successfully added 1000 items!");
        }
        else{
            System.out.println("Something went wrong when adding 1000 items. Size was " + stk.size());
        }
        for(int i = 999; i >= 0; i--){
            String removed = stk.pop();
            if(!removed.equals(""+i)){
                System.out.println("Items added were not in expected order.");
                break;
            }
        }
        if(stk.isEmpty()){
            System.out.println("1000 items successfully removed!");
        }
        else{
            System.out.println("Something went wrong when removing 1000 items");
        }
    }

    public static void test(MyQueue<String> q){
        //add first item
        q.enqueue("Hello World!");
        if(q.size() == 1 && q.peek().equals("Hello World!")){
            System.out.println("Successfully added one item!");
        }
        else{
            System.out.println("First item not added correctly");
        }
        //remove that item
        String s = q.dequeue();
        if(q.isEmpty() && q.size() == 0 && s.equals("Hello World!")){
            System.out.println("Successfully removed that one item!");
        } else{
            System.out.println("Incorrectly removed the first item.");
        }

        for(int i = 0; i < 1000; i++){
            q.enqueue(""+i);
        }
        if(q.size() == 1000){
            System.out.println("Successfully added 1000 items!");
        }
        else{
            System.out.println("Something went wrong when adding 1000 items. Size was " + q.size());
        }
        for(int i = 0; i < 1000; i++){
            String removed = q.dequeue();
            if(!removed.equals(""+i)){
                System.out.println("Items added were not in expected order.");
                break;
            }
        }
        if(q.isEmpty()){
            System.out.println("1000 items successfully removed!");
        }
        else{
            System.out.println("Something went wrong when removing 1000 items");
        }
    }

    public static void main(String[] args){
        System.out.println("ArrayQueue tests:");
        test(new ArrayQueue<String>());
        System.out.println();
        System.out.println("LinkedQueue tests:");
        test(new LinkedQueue<String>());
        System.out.println();
        System.out.println("ArrayStack tests:");
        test(new ArrayStack<String>());
        System.out.println();
        System.out.println("LinkedStack tests:");
        test(new LinkedStack<String>());
    }
        
}
