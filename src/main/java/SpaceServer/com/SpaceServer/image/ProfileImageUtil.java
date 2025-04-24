package SpaceServer.com.SpaceServer.image;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ProfileImageUtil {
    private static final List<String> PROFILE_IMAGES = List.of(
            "https://profilehackton.s3.ap-northeast-2.amazonaws.com/profile1.png",
            "https://profilehackton.s3.ap-northeast-2.amazonaws.com/profile2.png"
    );

    public String getRandomProfileUrl() {
        int index = new Random().nextInt(PROFILE_IMAGES.size());
        return PROFILE_IMAGES.get(index);
    }
}
