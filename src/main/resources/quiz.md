|fib|linkedlist| If you wanted to store lots of Customer objects and easily
insert a new object in between another one, what Collection class would you use?

---

|mc|E| Take a look at these two classes:

```
class Equity {
  public Equity(List<String> names) {
  }
}

class Stock extends Equity {
}
```

What's wrong with this code?

===

A. Won't compile: `Stock` doesn't have a **default** constructor

B. Won't compile: `Equity` doesn't have a **default** constructor

C. Can be _fixed_ by adding this code to Stock: `Stock(List<String> names) { }`

D. Can be _fixed_ by adding this code to Stock: `Stock(List<String> names) { super(names); }`

E. I really don't know

---

|mc|A,B,D,E| Which of the following are ways to create a list that can hold Strings (assume Java 8 or later)?

===

A. `List strings = new ArrayList();`

B. `List<String> strings = new ArrayList();`

C. `List<> strings = new ArrayList<String>();`

D. `List<String> strings = new ArrayList<String>();`

E. `List<String> strings = new ArrayList<>();`

---

|mc|E| Which of the following are _**preferred**_ ways to create a list that can hold `String`s (assume Java 8 or later)?

===

A. `List strings = new ArrayList();`

B. `List<String> strings = new ArrayList();`

C. `ArrayList<String> strings = new ArrayList<String>();`

D. `List<String> strings = new ArrayList<String>();`

E. `List<String> strings = new ArrayList<>();`

---

|fib|map,hashmap| If you wanted to store lots of Customer objects for easy access via their name,
what Java Collections type (data structure) would you use?
