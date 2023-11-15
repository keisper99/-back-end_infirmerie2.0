
package org.institutsaintjean.INFIRMERIE_IUSJC.mappers;


import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.InfirmiereDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.SignUpDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Entities.Infirmiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface InfirmiereMapper {

   InfirmiereDto toUserDto(Infirmiere infirmiere);


    @Mapping(target = "password", ignore = true)
    Infirmiere signUpToUser(SignUpDto signUpDto);

}















