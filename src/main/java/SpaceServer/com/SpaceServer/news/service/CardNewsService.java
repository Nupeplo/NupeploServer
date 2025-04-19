package SpaceServer.com.SpaceServer.news.service;

import SpaceServer.com.SpaceServer.news.entity.CardNewsEntity;
import SpaceServer.com.SpaceServer.news.repository.CardNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardNewsService {
    private final CardNewsRepository cardNewsRepository;

    public List<CardNewsEntity> getAllCardNews() {
        return cardNewsRepository.findAll();
    }

    public CardNewsEntity getCardNewsDetail(Long id) {
        return cardNewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 카드 뉴스를 찾을 수 없습니다."));
    }
}
