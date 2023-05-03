package sbu.cs;

import java.util.*;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

public class FindMultiples {
    public static class Task implements Runnable {
        int n ;
        List<Integer> result;
        public Task(int n, List<Integer> result) {
            this.n = n;
            this.result = result;
        }
        @Override
        public void run() {
            for (int i=1; i<=n; i++){
                if(i%3==0 || i%5==0 || i%7==0){
                    result.add(i);
                }
            }
        }
    }
    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public int getSum(int n) {
        int sum = 0;
        int threadCount = 3;
        List<Thread> threads = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        int chunkSize = n / threadCount;
        int end = chunkSize;
        for (int i = 0; i < threadCount; i++) {
            if (i == threadCount - 1) {
                end = n;
            }
            Thread thread = new Thread(new Task(end, result));
            thread.start();
            threads.add(thread);
            end += chunkSize;
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<Integer> uniqueResult = new HashSet<>(result);
        for (int num : uniqueResult) {
            sum += num;
        }
        return sum;
    }
    public static void main(String[] args) {
        FindMultiples findMultiples = new FindMultiples();
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int sum = findMultiples.getSum(n);
        System.out.println(sum);
    }
}
