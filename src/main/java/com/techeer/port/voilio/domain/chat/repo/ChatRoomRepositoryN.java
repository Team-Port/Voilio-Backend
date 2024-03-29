package com.techeer.port.voilio.domain.chat.repo;

import com.techeer.port.voilio.domain.chat.pubsub.RedisFollower;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ChatRoomRepositoryN {
  // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
  private final RedisMessageListenerContainer redisMessageListener;
  // 구독 처리 서비스
  private final RedisFollower redisFollowr;

  // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
  private Map<String, ChannelTopic> topics;

  @PostConstruct
  private void init() {
    topics = new HashMap<>();
  }

  /** 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다. */
  public void enterChatRoom(String roomUuid) {
    log.info("[ws] enterChatRoom :{}", roomUuid);
    ChannelTopic topic = topics.get(roomUuid);
    if (topic == null) topic = new ChannelTopic(roomUuid);
    redisMessageListener.addMessageListener(redisFollowr, topic);
    topics.put(roomUuid, topic);
  }

  public ChannelTopic getTopic(String roomId) {
    return topics.get(roomId);
  }
}
