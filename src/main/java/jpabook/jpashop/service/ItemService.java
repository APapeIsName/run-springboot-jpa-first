package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // merge 동작 방식 얘시 - 준영속 엔티티 값을 영속 엔티티 안에 넣어줌 -> null이 들어오면 값이 null로 변환됨 -> 변경 감지를 이용하는 게 제일 좋음
//    @Transactional
//    public Item updateItem(Long itemId, Book bookParam) {
//        Item findItem = itemRepository.findOne(itemId);
//        findItem.setPrice(bookParam.getPrice());
//        findItem.setName(bookParam.getName());
//        return findItem;
//    }

    // Controller에서는 직접 엔티티를 쓰지 말고 Service에서 사용하자(+Dto 사용도 하기) -> 트랜잭션이 있는 서비스 계층에서 영속 상태 엔티티 조회해야 더티 체킹이 됨
    // + setter 없이 entity 안에서 바로 추적 가능하게 만들기
    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return itemRepository.findOne(id);
    }
}
