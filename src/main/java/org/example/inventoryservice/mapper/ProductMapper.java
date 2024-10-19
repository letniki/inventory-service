package org.example.inventoryservice.mapper;

import org.example.inventoryservice.dto.ProductAvailability;
import org.example.inventoryservice.productapi.client.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDto.AvailabilityEnum mapAvailability(ProductAvailability productAvailability);

}
