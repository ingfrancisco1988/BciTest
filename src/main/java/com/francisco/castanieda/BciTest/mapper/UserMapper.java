package com.francisco.castanieda.BciTest.mapper;


import com.francisco.castanieda.BciTest.model.DTO.UserDTO;
import com.francisco.castanieda.BciTest.model.Entity.User;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory orikaMapperFactory) {
        orikaMapperFactory.classMap(User.class, UserDTO.class)
                .field("id","id")
                .field("username","username")
                .field("password","password")
                .byDefault()
                .register();
    }
}