package com.example.foodka.service.impl;

import com.example.foodka.dto.OrdersDto;
import com.example.foodka.dto.OrdersInputDto;
import com.example.foodka.dto.ProductOrderDto;
import com.example.foodka.dto.ResponseDto;
import com.example.foodka.model.Orders;
import com.example.foodka.model.Product;
import com.example.foodka.repository.OrdersRepository;
import com.example.foodka.repository.ProductOrderRepository;
import com.example.foodka.repository.ProductRepository;
import com.example.foodka.service.IdGenerator;
import com.example.foodka.service.OrdersService;
import com.example.foodka.service.mapper.OrdersMapper;
import com.example.foodka.service.mapper.ProductOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.foodka.appStatus.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final ProductRepository productRepository;
    private final IdGenerator idGenerator;
    private final ProductOrderMapper productOrderMapper;
    private final ProductOrderRepository productOrderRepository;

    @Override
    public ResponseDto<OrdersDto> add(OrdersInputDto ordersInputDto) {
        try{
            OrdersDto ordersDto = new OrdersDto();
            double total=0D;
            for (ProductOrderDto product : ordersInputDto.getProducts()) {
                Product pr = productRepository.findById(product.getProductId()).get();
                total+=(pr.getPrice() * product.getCount());
                product.setId(idGenerator.generate());
                productOrderRepository.save(productOrderMapper.toEntity(product));
            }
            ordersDto.setProducts(ordersInputDto.getProducts());
            ordersDto.setPrice(total);
            ordersDto.setId(idGenerator.generate());
            ordersDto.setAddress(ordersInputDto.getAddress());
            ordersDto.setDescription(ordersInputDto.getDescription());
            ordersDto.setUser(ordersInputDto.getUser());
            ordersDto.setStatus(0);
            ordersRepository.save(ordersMapper.toEntity(ordersDto));
            return ResponseDto.<OrdersDto>builder()
                    .message(OK)
                    .data(ordersDto)
                    .success(true)
                    .build();
        } catch (Exception e){
            return ResponseDto.<OrdersDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrdersDto>> getAll(Integer size, Integer page) {
        Long count = ordersRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size
        );

        try {
            Page<OrdersDto> all = ordersRepository.findAll(pageRequest).map(ordersMapper::toDto);

            return ResponseDto.<Page<OrdersDto>>builder()
                    .success(true)
                    .message(OK)
                    .data(all)
                    .build();
        } catch (Exception e){
            return ResponseDto.<Page<OrdersDto>>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrdersDto> getById(String id) {
        if (id == null) {
            return ResponseDto.<OrdersDto>builder()
                    .message(NULL_ID)
                    .build();
        }
        try {
            Optional<Orders> byId = ordersRepository.findById(id);
            if (byId.isPresent()) {
                return ResponseDto.<OrdersDto>builder()
                        .data(ordersMapper.toDto(byId.get()))
                        .success(true)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<OrdersDto>builder()
                    .message(NOT_FOUND)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<OrdersDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<OrdersDto>> getByUserId(String id) {
        if (id == null){
            return ResponseDto.<List<OrdersDto>>builder()
                    .message(NULL_ID)
                    .build();
        }
        try{
            List<Orders> allByCategoryId = ordersRepository.findAllByUserId(id);
            if (!allByCategoryId.isEmpty()){
                return ResponseDto.<List<OrdersDto>>builder()
                        .message(OK)
                        .success(true)
                        .data(allByCategoryId.stream().map(ordersMapper::toDto).toList())
                        .build();
            }else {
                return ResponseDto.<List<OrdersDto>>builder()
                        .message(NOT_FOUND)
                        .build();
            }
        }catch (Exception e){
            return ResponseDto.<List<OrdersDto>>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrdersDto> update(OrdersDto ordersDto) {
        if (ordersDto.getId() == null) {
            return ResponseDto.<OrdersDto>builder()
                    .message(NULL_ID)
                    .build();
        }

        Optional<Orders> optional = ordersRepository.findById(ordersDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<OrdersDto>builder()
                    .message(NOT_FOUND)
                    .build();
        }

        Orders orders = optional.get();

        if (ordersDto.getDescription() != null) {
            orders.setDescription(ordersDto.getDescription());
        }

        try {
            ordersRepository.save(orders);

            return ResponseDto.<OrdersDto>builder()
                    .message(OK)
                    .data(ordersMapper.toDto(orders))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<OrdersDto>builder()
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}
