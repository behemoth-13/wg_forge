package by.alexei.afanasyeu;

import static org.junit.Assert.assertTrue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;

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
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(6);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println(queue.isAtFullCapacity());
        ;
        queue.add(4);
        queue.add(5);
        queue.add(6);
        System.out.println(queue.isAtFullCapacity());
        queue.add(7);
        System.out.println(queue.get(5));
        ;
        queue.add(8);
        queue.add(9);
        System.out.println(queue.get(5));
        queue.add(10);
    }
}
