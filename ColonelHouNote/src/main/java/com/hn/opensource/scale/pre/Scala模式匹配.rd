val alice = new Person("Alice", 25)
val bob = new Person("Bob", 32)
val charlie = new Person("charlie", 32)
for(person <- List(alice, bob, charlie)){
  person match{
    case Person("Alice", 25) => println("Hi Alice!")
    case Person("Bob", 32) => println("Hi Alice!")
    case Person(name, age) => 
      println("Name: " + name + ", Age: " + age + " year.")
  }
}