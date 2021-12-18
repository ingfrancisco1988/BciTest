package com.francisco.castanieda.BciTest.mapper;

import com.francisco.castanieda.BciTest.exceptions.CustomException;
import com.francisco.castanieda.BciTest.model.DTO.ErrorInfoDTO;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMapper implements OrikaMapperFactoryConfigurer {



    @Override
    public void configure(MapperFactory orikaMapperFactory) {
        orikaMapperFactory.classMap(ErrorInfoDTO.class, CustomException.class)
                .field("timestamp","timestamp")
                .field("message","message")
                .byDefault()
                .register();
    }
}
