import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
       int a ;
        // a = new A().getA();
        a= new A(1).getA();
        System.out.println(new A());
}

}
class A extends Object {
    int a;
    public A() {}
    public A(int a) { this.a = a; }

    int getA() {
        return this.a;
    }
    @Override
    public String toString() {
        return "A class 입니다.";
    }
}


// 덧셈은 가능 하지만 곱셈은 불가능
// 생성자를 여러가지 만들 수 있다.
