num1 = int(input("Введіть перше число: "))
num2 = int(input("Введіть друге число: "))

total = num1 + num2

print("Сума чисел:", total)

number = int(input("Введіть число для перевірки: "))
is_prime = True

if number <= 1:
    is_prime = False
else:
    for i in range(2, number):
        if number % i == 0:
            is_prime = False
            break

if is_prime == True:
    print(number, "- це просте число")
else:
    print(number, "- це не просте число")


class Calculator:
    def add(self, a, b):
        return a + b

    def subtract(self, a, b):
        return a - b

    def multiply(self, a, b):
        return a * b

    def divide(self, a, b):
        if b == 0:
            return "На нуль ділити не можна"
        return a / b

calc = Calculator()

print("Додавання (10 + 5) =", calc.add(10, 5))
print("Віднімання (10 - 5) =", calc.subtract(10, 5))
print("Множення (10 * 5) =", calc.multiply(10, 5))
print("Ділення (10 / 5) =", calc.divide(10, 5))

class Library:
    def __init__(self):
        self.books = []

    def add_book(self, book_name):
        self.books.append(book_name)
        print("Додано книгу:", book_name)

    def remove_book(self, book_name):
        if book_name in self.books:
            self.books.remove(book_name)
            print("Видалено книгу:", book_name)
        else:
            print("Такої книги немає у списку.")

    def show_all(self):
        print("\nСписок усіх книг:")
        if len(self.books) == 0:
            print("Бібліотека порожня.")
        else:
            for book in self.books:
                print("-", book)
        print()

my_lib = Library()

my_lib.add_book("Кобзар")
my_lib.add_book("Гаррі Поттер")

my_lib.show_all()

my_lib.remove_book("Гаррі Поттер")
my_lib.show_all()