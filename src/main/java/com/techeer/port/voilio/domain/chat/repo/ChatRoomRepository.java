package com.techeer.port.voilio.domain.chat.repo;

//public interface ChatRoomRepository extends JpaRepository<ChatRoom, ChatRoomId> {
//
//  @Query("SELECT c FROM ChatRoom c WHERE c.user1.id = :userId OR c.user2.id = :userId")
//  List<ChatRoom> findAllByUserId(@Param("userId") long userId);
//
//  @Query(
//      "SELECT c FROM ChatRoom c WHERE (c.user1.id = :user1Id AND c.user2.id = :user2Id) OR"
//          + " (c.user1.id = :user2Id AND c.user2.id = :user1Id)")
//  Optional<ChatRoom> findByUser1AndUser2(
//      @Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
//}
