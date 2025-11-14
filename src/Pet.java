public abstract class Pet {
    private String name;
    private int age;

    public Pet(String name, int age) {  // constructor
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // Polymorphism: abstract method to get pet type
    public abstract String getPetType();

    @Override
    public String toString() {
        return getPetType() + ": " + name + ", Age: " + age;
    }
}
