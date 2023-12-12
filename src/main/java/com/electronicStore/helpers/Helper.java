package com.electronicStore.helpers;

import com.electronicStore.dtos.PageableResponse;
import com.electronicStore.dtos.UserDto;
import com.electronicStore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

public class Helper {
//    @Autowired
//    private ModelMapper modelMapper;

    public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){


        List<U> userList = page.getContent();
        List<V> userDtosList= userList.stream().map(user ->  new ModelMapper().map(user,type)).toList();

        PageableResponse<V> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(userDtosList);
        pageableResponse.setPageNumber(page.getNumber()+1);
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setTotalElement(page.getTotalElements());
        pageableResponse.setLastPage(page.isLast());

        return  pageableResponse;

    }
}
