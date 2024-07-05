package dev.check.mapper;

import dev.check.DTO.NewsletterDTO;
import dev.check.entity.Newsletter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NlMapper {

    public Newsletter newsletterDTOTonewsletter(NewsletterDTO nsDTO);

    public List<Newsletter>  newsletterDTOListTonewsletterList(List<NewsletterDTO> nsDTOList);

    @Mapping(target = "status", expression = "java(mapStatus(ns.getStatus()))" )
    @Mapping(target = "mess", expression = "java(mapMess(ns.getMess()))" )
    public NewsletterDTO newsletterToNewsletterDto(Newsletter ns);

    public List<NewsletterDTO> newsletterListTonewsletterDtoList(List<Newsletter> nsList);

    default String mapMess(Boolean mess){
        if(mess){
            return "Отправлено";
        }else{
            return "В Очереди";
        }
    }
    default String mapStatus(Boolean status){
        if(status){
            return "Доставлено";
        }else{
            return "Не Дошло(";
        }
    }
}
