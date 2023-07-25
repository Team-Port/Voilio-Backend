package com.techeer.port.voilio.domain.chat.repository.deprecate;

//@Slf4j
//@RequiredArgsConstructor
//@Repository
//public class ChatRoomRepositoryN {
//  // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
//  private final RedisMessageListenerContainer redisMessageListener;
//  // 구독 처리 서비스
//  private final RedisSubscriber redisSubscriber;
//
//  // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//  private Map<String, ChannelTopic> topics;
//
//  @PostConstruct
//  private void init() {
//    topics = new HashMap<>();
//  }
//
//  /** 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다. */
//  public void enterChatRoom(UUID roomId) {
//    log.info("[ws] enterChatRoom :{}", roomUuid);
//    ChannelTopic topic = topics.get(roomUuid);
//    if (topic == null) topic = new ChannelTopic(roomUuid);
//    redisMessageListener.addMessageListener(redisSubscriber, topic);
//    topics.put(roomUuid, topic);
//  }
//
//  public ChannelTopic getTopic(String roomId) {
//    return topics.get(roomId);
//  }
//}
