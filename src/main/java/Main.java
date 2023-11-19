public class Main {

    public static void main(String[] args) {
    }

    // 我有两个表，A和B，B的字段比A多，A和B是继承关系，设计dao层的接口，要求A和B都能用这个接口
    // 1. 抽象出一个基类，包含A和B的共有属性
    // 2. 抽象出一个接口，提供基本的增删改查方法
    // 3. A和B都继承基类，实现接口
    // 4. 通过泛型来提高代码的复用性
}