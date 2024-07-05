package dev.check.mapper;

import dev.check.DTO.NewsletterDTO;
import dev.check.entity.Newsletter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-05T12:54:06+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.6.jar, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class NlMapperImpl implements NlMapper {

    @Override
    public Newsletter newsletterDTOTonewsletter(NewsletterDTO nsDTO) {
        if ( nsDTO == null ) {
            return null;
        }

        Newsletter newsletter = new Newsletter();

        newsletter.setId( nsDTO.getId() );
        newsletter.setDate( nsDTO.getDate() );
        newsletter.setText( nsDTO.getText() );
        newsletter.setSubject( nsDTO.getSubject() );
        if ( nsDTO.getMess() != null ) {
            newsletter.setMess( Boolean.parseBoolean( nsDTO.getMess() ) );
        }
        if ( nsDTO.getStatus() != null ) {
            newsletter.setStatus( Boolean.parseBoolean( nsDTO.getStatus() ) );
        }

        return newsletter;
    }

    @Override
    public List<Newsletter> newsletterDTOListTonewsletterList(List<NewsletterDTO> nsDTOList) {
        if ( nsDTOList == null ) {
            return null;
        }

        List<Newsletter> list = new ArrayList<Newsletter>( nsDTOList.size() );
        for ( NewsletterDTO newsletterDTO : nsDTOList ) {
            list.add( newsletterDTOTonewsletter( newsletterDTO ) );
        }

        return list;
    }

    @Override
    public NewsletterDTO newsletterToNewsletterDto(Newsletter ns) {
        if ( ns == null ) {
            return null;
        }

        NewsletterDTO newsletterDTO = new NewsletterDTO();

        newsletterDTO.setId( ns.getId() );
        newsletterDTO.setDate( ns.getDate() );
        newsletterDTO.setText( ns.getText() );
        newsletterDTO.setSubject( ns.getSubject() );

        newsletterDTO.setStatus( mapStatus(ns.getStatus()) );
        newsletterDTO.setMess( mapMess(ns.getMess()) );

        return newsletterDTO;
    }

    @Override
    public List<NewsletterDTO> newsletterListTonewsletterDtoList(List<Newsletter> nsList) {
        if ( nsList == null ) {
            return null;
        }

        List<NewsletterDTO> list = new ArrayList<NewsletterDTO>( nsList.size() );
        for ( Newsletter newsletter : nsList ) {
            list.add( newsletterToNewsletterDto( newsletter ) );
        }

        return list;
    }
}
