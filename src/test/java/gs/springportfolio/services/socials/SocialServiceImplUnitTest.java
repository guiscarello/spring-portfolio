package gs.springportfolio.services.socials;

import gs.springportfolio.models.Social;
import gs.springportfolio.repos.SocialRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@ExtendWith(MockitoExtension.class)
class SocialServiceImplUnitTest {

    @Mock
    private SocialRepo socialRepo;
    private SocialServiceImpl underTestService;

    @BeforeEach
    void setUp() {
        underTestService = new SocialServiceImpl(socialRepo);
    }

    @Test
    void canGetAllSocialInfo() {
        //when
        underTestService.getAllSocialInfo();
        //then
        Mockito.verify(socialRepo).findAll();
    }

    @Test
    void canCreateSocialInfo() {
        //given
        Social socialInfoTest = new Social(
                "Twitter",
                "bi bi-twitter",
                "https://twitter.com/GuilleScarello"
        );
        //when
        underTestService.createSocialInfo(socialInfoTest);
        //then
        Mockito.verify(socialRepo).save(socialInfoTest);

    }

    @Test
    void canDeleteSocialInfo() throws Exception {
        Long id = 1L;
        underTestService.deleteSocialInfo(id);
        Mockito.verify(socialRepo).deleteById(id);
    }

}