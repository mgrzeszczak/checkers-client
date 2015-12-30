package pl.mg.checkers.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by maciej on 25.12.15.
 */
@Service
public class ThreadPoolService {

    private Executor threadPool = Executors.newCachedThreadPool();

    public void execute(Runnable r){
        threadPool.execute(r);
    }

}
