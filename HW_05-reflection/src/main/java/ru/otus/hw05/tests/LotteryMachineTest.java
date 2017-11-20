package ru.otus.hw05.tests;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ru.otus.hw05.annotations.*;
import static ru.otus.hw05.testframework.TestFramework.*;

public class LotteryMachineTest {
    LotteryMachine lotteryMachine;

    @Before
    public void beforeLotteryMachineTest() {
        System.out.println("Before LotteryMachineTest");
        lotteryMachine = new LotteryMachine(5);
    }

    @After
    public void afterLotteryMachineTest() {
        System.out.println("After LotteryMachineTest");
        lotteryMachine.dispose();
    }

    @Test
    public void oneEmail() throws InterruptedException {
        LotteryMachine machine = new LotteryMachine(2);
        List<String> result = machine.draw(Collections.singletonList("test"));
        assertEquals(1, result.size());
    }

    @Test
    public void fiveEmails() {
        List<String> result = lotteryMachine
                .draw(Arrays.asList("0", "1", "2", "3", "4"));
        assertEquals(5, result.size());
        assertTrue(result.contains("0"));
        assertTrue(result.contains("11"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("4"));
    }

}
