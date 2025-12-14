import functions.*;
import functions.basic.*;

import java.io.*;

public class Main {

    // метод для вывода всех точек через геттеры
    public static void printAllPoints(TabulatedFunction func) {
        for (int i = 0; i < func.getPointsCount(); i++) {
            double x = func.getPointX(i);
            double y = func.getPointY(i);
            System.out.println("Точка "+ i +":" + "(" +x + "," + y +")");
        }
    }

    public static void main(String[] args) {
        // оборачиваем в try-catch для обработки исключений
        try {
            // создаем функцию f(x) = 2x + 1
            double[] xValues = {-2, -1, 0, 1, 2, 3, 4};
            double[] yValues = {-3, -1, 1, 3, 5, 7, 9};
            double leftX = xValues[0];
            double rightX = xValues[xValues.length - 1];

            // используем интерфейс TabulatedFunction и ArrayTabulatedFunction
            TabulatedFunction linearFunc = new ArrayTabulatedFunction(leftX, rightX, yValues);

            // небольшая вводная часть
            System.out.println("f(x) = 2x + 1 на [" + leftX + ", " + rightX + "]");
            System.out.println("Точек: " + linearFunc.getPointsCount());

            // вывод всех точек
            System.out.println("Точки после создания функции:");
            printAllPoints(linearFunc);

            // тестирование  промежуточные значениями (интерполяция)
            System.out.println(" Тестирование интерполяции (промежуточные значения) ");
            // между точками (-1, -1) и (0, 1)
            System.out.println("Интерполяция между точками (-1,-1) и (0,1):");
            double testX1 = -0.5;  // середина между -1 и 0
            double ex1 = 2 * testX1 + 1;  // по формуле f(x) = 2x + 1
            double ac1 = linearFunc.getFunctionValue(testX1);
            System.out.println("f(" + testX1 + ") = " + ac1 + " (ожидается: "  + ex1 + ")");

            // между точками (0, 1) и (1, 3)
            double testX2 = 0.5;  // середина между 0 и 1
            double ex2 = 2 * testX2 + 1;
            double ac2 = linearFunc.getFunctionValue(testX2);
            System.out.println("f(" + testX2 + ") = " + ac2 + " (ожидается: " + ex2 + ")");

            // проверки за пределами массива
            System.out.println("точка левее границы:");
            double testX3 = -3.0;  // левее левой границы
            double ac3 = linearFunc.getFunctionValue(testX3);
            System.out.println("f(" + testX3 + ") = " + ac3 + " (слева)");

            System.out.println("точка правее границы:");
            double testX4 = 5.0;   // правее правой границы
            double actualY4 = linearFunc.getFunctionValue(testX4);
            System.out.println("f(" + testX4 + ") = " + actualY4 + " (справа)");

            // тестируем значения
            System.out.println("\nТест самих значений");
            double[] testPoints = {-4, -2, 0, 2, 4, 6};
            for (double x : testPoints) {
                double y = linearFunc.getFunctionValue(x);
                System.out.println("f(" + x + ") = " + y);
            }

            // меняем точки
            linearFunc.setPointY(3, 10);
            // точки после изменения y
            System.out.println("Точки после setPointY(3, 10):");
            printAllPoints(linearFunc);

            // оборачиваем setPointX в try-catch (может выбросить InappropriateFunctionPointException)
            try {
                linearFunc.setPointX(1, -0.7);
                System.out.println("setPointX выполнен успешно");
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Ошибка setPointX: " + e.getMessage());
            }

            // вывод точки после изменения x
            System.out.println("Точки после setPointX(1, -0.7):");
            printAllPoints(linearFunc);

            // добавляем и удаляем
            // оборачиваем addPoint также в try-catch
            try {
                linearFunc.addPoint(new FunctionPoint(2.5, 6));
                System.out.println("addPoint выполнен успешно");
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Ошибка addPoint: " + e.getMessage());
            }

            // точки после добавления
            System.out.println("Точки после addPoint(2.5, 6):");
            printAllPoints(linearFunc);

            // оборачиваем также deletePoint в try-catch
            try {
                linearFunc.deletePoint(2);
                System.out.println("deletePoint выполнен успешно");
            } catch (Exception e) {
                System.out.println("Ошибка deletePoint: " + e.getMessage());
            }

            // точки после удаления
            System.out.println("Точки после deletePoint(2):");
            printAllPoints(linearFunc);

            // финальная проверка
            System.out.println("Финальный результат:");
            System.out.println("Всего точек:" + linearFunc.getPointsCount());
            System.out.println("f(2.5)=" + linearFunc.getFunctionValue(2.5));

        } catch (Exception e) {  //
            System.out.println("Программа завершилась с ошибкой: " + e.getMessage());
            e.printStackTrace();
        }

        // тестирование LinkedListTabulatedFunction - заменяем один класс на другой
        System.out.println("-----  Тестирование LinkedListTabulatedFunction ---------");
        try {
            double[] xValues = {-2, -1, 0, 1, 2, 3, 4};
            double[] yValues = {-3, -1, 1, 3, 5, 7, 9};
            double leftX = xValues[0];
            double rightX = xValues[xValues.length - 1];

            // используем LinkedListTabulatedFunction
            LinkedListTabulatedFunction listFunc = new LinkedListTabulatedFunction(leftX, rightX, yValues);

            System.out.println("LinkedListTabulatedFunction создан успешно");
            System.out.println("Исходное количество точек: " + listFunc.getPointsCount());
            System.out.println("Исходные точки:");
            printAllPoints(listFunc);

            // удаление точки с индексом 0
            System.out.println("удаление точки с индексом 0");
            System.out.println("Удаляем первую точку (индекс 0): (" + listFunc.getPointX(0) + ", " + listFunc.getPointY(0) + ")");

            listFunc.deletePoint(0);

            System.out.println("После удаления:");
            System.out.println("Количество точек: " + listFunc.getPointsCount());
            System.out.println("Первая точка теперь: (" + listFunc.getPointX(0) + ", " + listFunc.getPointY(0) + ")");
            System.out.println("Все точки после удаления первой:");
            printAllPoints(listFunc);

            // добавление и удаление из разных позиций
            System.out.println("Добавление и удаление");

            // добавляем новую точку в начало
            listFunc.addPoint(new FunctionPoint(-3, -5));
            System.out.println("После добавления точки (-3, -5) в начало:");
            System.out.println("ВСЕ точки после добавления:");
            printAllPoints(listFunc);  // вывод всех точек(добавил после сообщения)

            // снова удаляем первую точку
            listFunc.deletePoint(0);
            System.out.println("После повторного удаления первой точки:");
            System.out.println("все точки после удаления:");
            printAllPoints(listFunc);  // вывод всех точек(также добавил)

            // удаление последней точки
            System.out.println("Удаление последней точки");
            int lastIndex = listFunc.getPointsCount() - 1;
            System.out.println("Удаляем последнюю точку (индекс " + lastIndex + "): (" + listFunc.getPointX(lastIndex) + ", " + listFunc.getPointY(lastIndex) + ")");

            listFunc.deletePoint(lastIndex);
            System.out.println("После удаления последней точки, новая последняя точка: (" + listFunc.getPointX(listFunc.getPointsCount() - 1) + ", " + listFunc.getPointY(listFunc.getPointsCount() - 1) + ")");

            //  иизменение точек в LinkedList
            System.out.println("Изменение значений точек ");
            System.out.println("Меняем значение второй точки (индекс 1)");
            double oldY = listFunc.getPointY(1);
            listFunc.setPointY(1, oldY * 10);  //
            System.out.println("Y изменен с " + oldY + " на " + listFunc.getPointY(1));

            // проверка интерполяции в LinkedList
            System.out.println("Интерполяция в LinkedList ");
            double testX = 0.5;  // между точками
            double interpolatedY = listFunc.getFunctionValue(testX);
            System.out.println("Интерполяция f(" + testX + ") = " + interpolatedY);

            // проверка исключения FunctionPointIndexOutOfBoundsException
            try {
                listFunc.getPointX(100); // неверный индекс
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("Поймано FunctionPointIndexOutOfBoundsException: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Ошибка при тестировании LinkedList: " + e.getMessage());
            e.printStackTrace();
        }

        // проверка исключений в конструкторах
        System.out.println("проверка исключений ");

        try {
            // IllegalArgumentException: левая граница >= правой
            new ArrayTabulatedFunction(10, 5, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("поймано IllegalArgumentException (границы): " + e.getMessage());
        }

        try {
            // IllegalArgumentException: точек < 2
            new LinkedListTabulatedFunction(0, 10, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано IllegalArgumentException (точек < 2): " + e.getMessage());
        }
        // ЗАДАНИЕ 8: тесты
        System.out.println("ЗАДАНИЕ 8: тесты работ классов");
        try {
            //  Sin и Cos на отрезке от 0 до π с шагом 0,1
            System.out.println("1. Sin и Cos от 0 до π с шагом 0.1:");
            System.out.println("x         sin(x)       cos(x)");
            Sin sinFunc = new Sin();
            Cos cosFunc = new Cos();

            for (double x = 0; x <= Math.PI + 0.05; x += 0.1) {
                System.out.println(x + "      " + sinFunc.getFunctionValue(x) + "      " + cosFunc.getFunctionValue(x));
            }

            // табулированные аналоги (10 точек)
            System.out.println(" Табулированные аналоги (10 точек):");
            System.out.println("x         Sin (точный) Sin (табулированный) Cos (точный) Cos (табулированный)");
            TabulatedFunction tabSin = TabulatedFunctions.tabulate(sinFunc, 0, Math.PI, 10);
            TabulatedFunction tabCos = TabulatedFunctions.tabulate(cosFunc, 0, Math.PI, 10);

            for (double x = 0; x <= Math.PI + 0.05; x += 0.1) {
                double exactSin = sinFunc.getFunctionValue(x);
                double exactCos = cosFunc.getFunctionValue(x);
                double tabSinVal = tabSin.getFunctionValue(x);
                double tabCosVal = tabCos.getFunctionValue(x);
                System.out.println(x + " " + exactSin + " " + tabSinVal + " " + exactCos + " " + tabCosVal);
            }

            //  сумма квадратов sin² + cos² (должно быть ≈1)
            System.out.println("умма квадратов sin² + cos²:");
            System.out.println("Теория: sin²(x) + cos²(x) = 1 для любого x");
            System.out.println("x         sin²+cos² (10 точек)  sin²+cos² (20 точек)");

            // с 10 точками
            TabulatedFunction tabSin10 = TabulatedFunctions.tabulate(sinFunc, 0, Math.PI, 10);
            TabulatedFunction tabCos10 = TabulatedFunctions.tabulate(cosFunc, 0, Math.PI, 10);
            Function sinSquared10 = Functions.power(tabSin10, 2);
            Function cosSquared10 = Functions.power(tabCos10, 2);
            Function sum10 = Functions.sum(sinSquared10, cosSquared10);

            // с 20 точками
            TabulatedFunction tabSin20 = TabulatedFunctions.tabulate(sinFunc, 0, Math.PI, 20);
            TabulatedFunction tabCos20 = TabulatedFunctions.tabulate(cosFunc, 0, Math.PI, 20);
            Function sinSquared20 = Functions.power(tabSin20, 2);
            Function cosSquared20 = Functions.power(tabCos20, 2);
            Function sum20 = Functions.sum(sinSquared20, cosSquared20);

            for (double x = 0; x <= Math.PI + 0.05; x += 0.1) {
                double value10 = sum10.getFunctionValue(x);
                double value20 = sum20.getFunctionValue(x);
                System.out.println(x + " " + value10 + " " + value20);
            }

            // Экспонента: текстовый файл
            System.out.println("Экспонента: запись/чтение текстового файла (exp.txt)");

            // создаем табулированную экспоненту
            Exp expFunc = new Exp();
            TabulatedFunction tabExp = TabulatedFunctions.tabulate(expFunc, 0, 10, 11);

            // записываем в файл
            try (FileWriter writer = new FileWriter("exp.txt")) {
                TabulatedFunctions.writeTabulatedFunction(tabExp, writer);
            }
            // читаем из файла
            try (FileReader reader = new FileReader("exp.txt")) {
                TabulatedFunction readExp = TabulatedFunctions.readTabulatedFunction(reader);
                System.out.println("Сравнение исходной и прочитанной функции:");
                System.out.println("x         Исходная e^x  Прочитанная  Разница");

                for (double x = 0; x <= 10; x += 1) {
                    double original = tabExp.getFunctionValue(x);
                    double read = readExp.getFunctionValue(x);
                    double diff = Math.abs(original - read);
                    System.out.printf("%.0f      %.6f      %.6f      %.10f%n",
                            x, original, read, diff);
                }
            }

            // логарифм(бинарный файл)
            System.out.println("(логарифм, запись/чтение бинарного файла (log.bin)");

            // создаем табулированный логарифм (натуральный, основание e)
            Log lnFunc = new Log(Math.E);  // натуральный логарифм
            TabulatedFunction tabLog = TabulatedFunctions.tabulate(lnFunc, 0.1, 10, 11);

            // записываем в бинарный файл
            try (FileOutputStream fos = new FileOutputStream("log.bin")) {
                TabulatedFunctions.outputTabulatedFunction(tabLog, fos);
            }

            // читаем из бинарного файла
            try (FileInputStream fis = new FileInputStream("log.bin")) {
                TabulatedFunction readLog = TabulatedFunctions.inputTabulatedFunction(fis);

                System.out.println("Сравнение исходной и прочитанной функции:");
                System.out.println("x         Исходная ln(x)  Прочитанная  Разница");

                for (double x = 1; x <= 10; x += 1) {
                    double original = tabLog.getFunctionValue(x);
                    double read = readLog.getFunctionValue(x);
                    double diff = Math.abs(original - read);
                    System.out.println(x + " " + original + " " + read + " " + diff);

                }
            }
        } catch (Exception e) {
            System.err.println("ошибка в каких то тестах " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("небольшой тест сериализации");
         // создаем функциюс некоторыми параметрами
        LinkedListTabulatedFunction original = new LinkedListTabulatedFunction(
                0, 3, new double[]{0, 1, 4, 9}
        );
        System.out.println("оригинальная функция:");
        System.out.println("f(1) = " + original.getFunctionValue(1) + " (ожидается 1.0)");
        System.out.println("f(2) = " + original.getFunctionValue(2) + " (ожидается 4.0)");

        try {
            // cериализуем в байтовый массив
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(original);
            oos.close();

            System.out.println("функця cериализовано " + baos.size() + " байт");

            // десериализуем обратно
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            LinkedListTabulatedFunction restored = (LinkedListTabulatedFunction) ois.readObject();
            ois.close();

            System.out.println("восстановленная функция:");
            System.out.println("f(1) = " + restored.getFunctionValue(1) + " (ожидается 1.0)");
            System.out.println("f(2) = " + restored.getFunctionValue(2) + " (ожидается 4.0)");

            // проверка на то, что одинаковые
            if (Math.abs(original.getFunctionValue(1) - restored.getFunctionValue(1)) < 0.0001 &&
                    Math.abs(original.getFunctionValue(2) - restored.getFunctionValue(2)) < 0.0001) {
                System.out.println("Функции индентичны");
            } else {
                System.out.println("Функции различаются");
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        }
    }
}