
**Leopard 嵌入式内存存储数据库（自带ORM客户端）**

只需要可控本项目到本地打包，创建maven依赖或者gradle依赖即可。

启动服务端和客户端

     // 创建客户端
    private final LeopardClient leopardClient =
    // 启动DB服务
    newLeopardClient.Builder().startDBServer().build();
    
    // 切换数据库  如果不存在则会自己创建
    leopardClient.switchDB("default");

本项目基于ORM风格进行CRUD

实体类demo

    public class Student implements Serializable {

    @PrimaryKey
    private String id;
    private String name;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


查询数据

    @Test
    public void testSelect() throws Throwable {

        // 构建查询条件
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).le("age", 30);

        // 得到结果
        Collection<Student> collection = leopardClient
                // 输入条件表达式
                .input(expression)
                // 表示需要进行查询操作
                .select()
                // 执行
                .execute();

        System.err.println(collection);
    }

保存数据

     Student student = new Student();

        student.setAge(20);
        student.setId(UUID.randomUUID().toString());
        student.setName("Jack");

        // 存储数据  如果不存在会自己创建
        int save = leopardClient.input(student).save().execute();

删除数据

     @Test
    public void delete() {
        //
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("age", 20);
        int execute = leopardClient.input(expression).delete().execute();
        System.err.println(execute);
    }

修改数据

    @Test
    public void update() {
        IntermediateExpression<Student> expression = IntermediateExpression.buildFor(Student.class).eq("age", 20);
        int update = leopardClient.input(expression).update().set("age", 90).execute();
        System.err.println(update);
    }
