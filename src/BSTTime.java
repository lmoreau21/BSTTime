import java.io.FileNotFoundException;
import java.util.Random;

public class BSTTime {
    static int min=100000;
    static Node root = null;
    
    public static void main(String[] args) throws FileNotFoundException {
        Random rand = new Random();
        
        int maxNumber = 32767;
        int maxSize = 100000;
        
        rand.nextBoolean();
        rand.nextInt();
     
        rand.nextInt(0, 1);
        long start = System.currentTimeMillis();
        int num1 = rand.nextInt(maxNumber);
        int num2 = rand.nextInt(maxNumber);
        root = IN(null,num1,num2);
        for(int i=0; i<maxSize-1; i++){
            num1 = rand.nextInt(maxNumber);
            num2 = rand.nextInt(maxNumber);
            IN(root, num1, num2);
        }
        System.out.println("Insert: "+(System.currentTimeMillis()-start)+" (ms.)");
        
        start = System.currentTimeMillis();
        int numRandomRMQ = 1000;
        for(int i=0; i<numRandomRMQ; i++){
            num1 = rand.nextInt(maxNumber);
            num2 = rand.nextInt(maxNumber);
            if(num1 < num2) RMQ(root, num1, num2);
            else RMQ(root, num2, num1);      
        }
        System.out.println("RMQ: "+(System.currentTimeMillis()-start)+" (ms.)");
        
        start = System.currentTimeMillis();
        for(int i=0; i<numRandomRMQ; i++){
            num1 = rand.nextInt(maxNumber);
            num2 = rand.nextInt(maxNumber);
            if(num1 < num2) RangeMin(root, num1, num2);
            else RangeMin(root, num2, num1);      
        }
        System.out.println("Range Min: "+(System.currentTimeMillis()-start)+" (ms.)");
    } 

    public static Node IN(Node x, int key, int data){
        if (x == null) {
            x = new Node(key, data);
            x.setMindata(data);
            return x;
        }
        if(key < x.getKey()){
            x.setLeft(IN(x.getLeft(), key, data));
        }else if(key > x.getKey()){
            x.setRight(IN(x.getRight(), key, data));
        }else if(x.getKey()==key){
            if(x.getData()>data) x.setData(data);
        }
        recalcMindata(x);
        return x;       
    }
    
    //checks the data of every node in the range and returns the smallest data value
    public static void RMQ(Node x, int k1, int k2){
        if(x == null) return;
        if(x.getKey() > k1)
            RMQ(x.getLeft(), k1, k2);
        if(x.getKey() < k2)
            RMQ(x.getRight(),k1,k2);
        if(x.getKey()>=k1 && x.getKey()<=k2)
            if(min > x.getData())
                min = x.getData();
    }
    
    //Redefines mindata for every node that gets inserted
    public static void recalcMindata(Node x){
        if(x != null)
            x.setMindata(x.getData());
        if(x.getLeft()!= null)
            x.setMindata(Integer.min(x.getMindata(),x.getLeft().getMindata()));
        if(x.getRight()!=null)
            x.setMindata(Integer.min(x.getMindata(),x.getRight().getMindata()));
    }
    
    //this is the more effective RMQ by using mindata
    public static int RangeMin(Node x,int k1,int k2){
        while (x!=null && (k1 > x.getKey() || x.getKey() > k2)) {
            if (k1 < x.getKey() && k2 < x.getKey())
                x = x.getLeft();
            else 
                x = x.getRight();
        }
        int minOne = 200000;
        if(x != null && x.getLeft()!=null) minOne = RangeMinRight(x.getLeft(), k1);
        int minTwo = 200000;
        if(x != null && x.getRight()!=null) minTwo = RangeMinLeft(x.getRight(), k2);     
        return Integer.min(minOne, minTwo);
    }
    public static int RangeMinRight(Node root, int k1) {
        int minNumber = root.getData();
        if (k1 < root.getKey()&&root.getRight()!=null) {            
            minNumber = Integer.min(minNumber, root.getRight().getMindata());
            if(root.getLeft()!=null)
                minNumber = RangeMinRight(root.getLeft(), k1);
        }else if (k1 > root.getKey()&&root.getRight()!=null)
            minNumber = RangeMinRight(root.getRight(), k1);
        else if(root.getRight()!=null){            
            minNumber = Integer.min(minNumber, root.getRight().getMindata());
        }
        return minNumber;
    }
    public static int RangeMinLeft(Node root, int k2){
        int minNumber = root.getData();
        if (k2 > root.getKey()&&root.getLeft()!=null) {          
            minNumber = Integer.min(minNumber, root.getLeft().getMindata());
            if(root.getRight()!=null)
                minNumber = RangeMinLeft(root.getRight(), k2);
        }else if (k2 < root.getKey()&&root.getLeft()!=null)
            minNumber = RangeMinLeft(root.getLeft(), k2);
        else if(root.getLeft()!=null){
            minNumber = Integer.min(minNumber,root.getLeft().getMindata());
        }
        return minNumber;
    }
}


class Node{
    private int key;
    private int data;
    private int mindata;
    private Node left, right;

    public Node(int key, int data){
        this.key = key;
        this.data = data;
    }

    public int getMindata(){
        return mindata;
    }
    
     public void setMindata(int num){
        mindata = num;
    }
    
    public int getData() {
        return data;
    }

    public void setData(int data){
        this.data = data;
    }
 
    public int getKey() {
        return key;
    }
 
    public Node getLeft() {
        return left;
    }
 
    public void setLeft(Node left) {
        this.left = left;
    }
 
    public Node getRight() {
        return right;
    }
 
    public void setRight(Node right) {
        this.right = right;
    }
}