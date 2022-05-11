package gs.springportfolio.api;

import gs.springportfolio.models.Social;
import gs.springportfolio.services.socials.SocialServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class SocialControllerUnitTest {

    private SocialController underTestController;
    private  SocialServiceImpl socialServiceMock;
    private List<Social> socialListMock = new ArrayList<>();

    @BeforeEach
    void setUp() {
        socialServiceMock = Mockito.mock(SocialServiceImpl.class);
        underTestController = new SocialController(socialServiceMock);
        socialListMock.add( new Social("Twitter",
                "bi bi-twitter",
                "https://twitter.com/GuilleScarello"));
        socialListMock.add( new Social("Facebook",
                "bi bi-facebook",
                "https://facebook.com/guille"));
    }

    @Test
    void canGetSocialInfo() {
        when(socialServiceMock.getAllSocialInfo()).thenReturn(this.socialListMock);
        ResponseEntity<List<Social>> response = underTestController.getSocialInfo();
        assertThat(response)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
        assertSame(socialListMock, response.getBody());
    }

    @Test
    void canInsertSocialInfo() {
        Social newSocial = socialListMock.get(0);
        when(socialServiceMock.createSocialInfo(newSocial)).thenReturn(newSocial);
        ResponseEntity<Social> response = underTestController.insertSocialInfo(
                socialListMock.get(0).getName(),
                socialListMock.get(0).getBsClassIcon(),
                socialListMock.get(0).getLink()
        );
        assertThat(response)
                .isNotNull()
                .isInstanceOf(ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       // assertSame(socialListMock.get(0), newSocialInfo.getBody());
    }

    @Test
    void deleteSocialInfo() {
    }
}