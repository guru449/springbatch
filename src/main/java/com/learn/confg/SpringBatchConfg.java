package com.learn.confg;

import com.learn.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class SpringBatchConfg {
    @Bean
    public Job job(JobBuilderFactory factory, StepBuilderFactory stepBuilderFactory, ItemReader<User> itemReader,
                   ItemProcessor<User,User> itemprocessor, ItemWriter<User> itemWriter) {
        //Step has reader processro and writer
        Step step = stepBuilderFactory.get("ETL-FILE-LOAD").<User, User>chunk(100).
                reader(itemReader).processor(itemprocessor)
                .writer(itemWriter).build();


        return factory.get("ETL-Load").incrementer(new RunIdIncrementer()).start(step).build();
    }
        @Bean
       public  FlatFileItemReader<User>flatFileItemReader(@Value ("${imput}") Resource resource)
        {
            FlatFileItemReader<User> flatFileItemReader=new FlatFileItemReader<User>();
            flatFileItemReader.setResource(resource);
            flatFileItemReader.setName("CSV-Reader");
            flatFileItemReader.setLinesToSkip(1);
            flatFileItemReader.setLineMapper(lineMapper());
            return flatFileItemReader;
        }
        @Bean
    public LineMapper<User> lineMapper()
    {
        DefaultLineMapper<User>defaultLineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id","name","dept","salary"});
        BeanWrapperFieldSetMapper<User> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}


