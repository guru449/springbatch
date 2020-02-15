package com.learn.Batch;

import com.learn.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor  implements ItemProcessor<User, User> {
    @Override
    public User process(User user) throws Exception {
        return null;
    }
}
