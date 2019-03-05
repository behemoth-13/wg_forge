package by.alexei.afanasyeu;

import static org.junit.Assert.assertTrue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;

import java.util.Queue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Queue<Integer> queue = new CircularFifoQueue<>(6);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        ;
        queue.add(4);
        queue.add(5);
        queue.add(6);

        queue.add(7);
        System.out.println(queue.peek());
        ;
        queue.add(8);
        queue.add(9);
        queue.add(10);
        System.out.println(queue.peek());
    }
}
