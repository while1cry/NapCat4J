package me.while1cry.napcat4j.entity.message;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MessageTests {

    private final ObjectMapper mapper = new ObjectMapper();

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {

        @Test
        @DisplayName("should build empty message")
        void testBuildEmptyMessage() {
            Message message = Message.builder().build();
            assertNotNull(message);
            assertEquals("[]", message.toJson());
            assertEquals("", message.toCQ());
        }

        @Test
        @DisplayName("should build message with single text")
        void testBuildSingleText() {
            Message message = Message.builder()
                    .text("Hello World")
                    .build();
            
            assertNotNull(message);
            assertEquals("[{\"type\":\"text\",\"data\":{\"text\":\"Hello World\"}}]", message.toJson());
            assertEquals("Hello World", message.toCQ());
        }

        @Test
        @DisplayName("should build message with multiple texts")
        void testBuildMultipleTexts() {
            Message message = Message.builder()
                    .text("Hello")
                    .text(" ")
                    .text("World")
                    .build();
            
            assertNotNull(message);
            assertEquals("[{\"type\":\"text\",\"data\":{\"text\":\"Hello\"}},{\"type\":\"text\",\"data\":{\"text\":\" \"}},{\"type\":\"text\",\"data\":{\"text\":\"World\"}}]",
                    message.toJson());
            assertEquals("Hello World", message.toCQ());
        }

        @Test
        @DisplayName("should build message with face")
        void testBuildWithFace() {
            Message message = Message.builder()
                    .face("123")
                    .build();
            
            assertNotNull(message);
            assertEquals("[{\"type\":\"face\",\"data\":{\"id\":\"123\"}}]", message.toJson());
            assertEquals("[CQ:face,id=123]", message.toCQ());
        }

        @Test
        @DisplayName("should build complex message")
        void testBuildComplexMessage() {
            Message message = Message.builder()
                    .text("Hello")
                    .face("100")
                    .text("World")
                    .build();
            
            assertNotNull(message);
            assertEquals("Hello[CQ:face,id=100]World", message.toCQ());
        }

        @Test
        @DisplayName("should handle empty text")
        void testHandleEmptyText() {
            Message message = Message.builder()
                    .text("")
                    .build();
            
            assertNotNull(message);
            assertEquals("[{\"type\":\"text\",\"data\":{\"text\":\"\"}}]", message.toJson());
            assertEquals("", message.toCQ());
        }

        @Test
        @DisplayName("should handle special characters in text")
        void testHandleSpecialCharacters() {
            Message message = Message.builder()
                    .text("Hello, [World] & Test")
                    .build();
            
            assertEquals("Hello, &#91;World&#93; &amp; Test", message.toCQ());
        }

        @Test
        @DisplayName("should handle special characters in face id")
        void testHandleSpecialCharactersInFaceId() {
            Message message = Message.builder()
                    .face("test,123")
                    .build();
            
            assertEquals("[CQ:face,id=test&#44;123]", message.toCQ());
        }

        @Test
        @DisplayName("should build at specific user")
        void testBuildAtSpecificUser() {
            Message message = Message.builder()
                    .at("123456789")
                    .build();

            assertEquals("[{\"type\":\"at\",\"data\":{\"qq\":\"123456789\"}}]", message.toJson());
            assertEquals("[CQ:at,qq=123456789]", message.toCQ());
        }

        @Test
        @DisplayName("should build json message")
        void testBuildJsonMessage() {
            Message message = Message.builder()
                    .json("{\"key\":\"value\"}")
                    .build();

            assertEquals("[{\"type\":\"json\",\"data\":{\"data\":\"{\\\"key\\\":\\\"value\\\"}\"}}]", message.toJson());
            assertEquals("[CQ:json,data={\"key\":\"value\"}]", message.toCQ());
        }
    }

    @Nested
    @DisplayName("JSON Serialization Tests")
    class JsonSerializationTests {

        @Test
        @DisplayName("should serialize single text to JSON")
        void testSerializeSingleText() {
            Message message = Message.builder()
                    .text("Test Message")
                    .build();
            
            String json = message.toJson();
            assertEquals("[{\"type\":\"text\",\"data\":{\"text\":\"Test Message\"}}]", json);
        }

        @Test
        @DisplayName("should serialize complex message to JSON")
        void testSerializeComplexMessage() {
            Message message = Message.builder()
                    .text("Hello")
                    .face("123")
                    .text("World")
                    .build();
            
            String json = message.toJson();
            assertEquals("[{\"type\":\"text\",\"data\":{\"text\":\"Hello\"}},{\"type\":\"face\",\"data\":{\"id\":\"123\"}},{\"type\":\"text\",\"data\":{\"text\":\"World\"}}]",
                    json.replaceAll("\\s+", ""));
        }

        @Test
        @DisplayName("should deserialize from JSON string")
        void testDeserializeFromJsonString() {
            String json = "[{\"type\":\"text\",\"data\":{\"text\":\"Hello World\"}}]";
            Message message = Message.fromJson(json);
            
            assertNotNull(message);
            assertEquals("Hello World", message.toCQ());
        }

        @Test
        @DisplayName("should deserialize complex JSON")
        void testDeserializeComplexJson() {
            String json = "[{\"type\":\"text\",\"data\":{\"text\":\"Hello\"}},{\"type\":\"face\",\"data\":{\"id\":\"123\"}},{\"type\":\"text\",\"data\":{\"text\":\"World\"}}]";
            Message message = Message.fromJson(json);
            
            assertNotNull(message);
            assertEquals("Hello[CQ:face,id=123]World", message.toCQ());
        }

        @Test
        @DisplayName("should deserialize from ObjectNode")
        void testDeserializeFromObjectNode() {
            ArrayNode dataArray = mapper.createArrayNode()
                    .add(mapper.createObjectNode()
                            .put("type", "text")
                            .set("data", mapper.createObjectNode().put("text", "Test")));

            Message message = Message.fromJson(dataArray);
            assertNotNull(message);
            assertEquals("Test", message.toCQ());
        }

        @Test
        @DisplayName("should throw exception for invalid JSON")
        void testThrowExceptionForInvalidJson() {
            String invalidJson = "invalid json";
            assertThrows(RuntimeException.class, () -> Message.fromJson(invalidJson));
        }

        @Test
        @DisplayName("should handle empty JSON array")
        void testHandleEmptyJsonArray() {
            String json = "[]";
            Message message = Message.fromJson(json);
            
            assertNotNull(message);
            assertEquals("", message.toCQ());
        }
    }

    @Nested
    @DisplayName("CQ Code Tests")
    class CQCodeTests {

        @Test
        @DisplayName("should parse simple text")
        void testParseSimpleText() {
            Message message = Message.fromCQ("Hello World");
            
            assertNotNull(message);
            assertEquals("Hello World", message.toCQ());
        }

        @Test
        @DisplayName("should parse face CQ code")
        void testParseFaceCQCode() {
            Message message = Message.fromCQ("[CQ:face,id=123]");
            
            assertNotNull(message);
            assertEquals("[CQ:face,id=123]", message.toCQ());
        }

        @Test
        @DisplayName("should parse complex CQ string")
        void testParseComplexCQString() {
            Message message = Message.fromCQ("Hello[CQ:face,id=100]World");
            
            assertNotNull(message);
            assertEquals("Hello[CQ:face,id=100]World", message.toCQ());
        }

        @Test
        @DisplayName("should parse multiple CQ codes")
        void testParseMultipleCQCodes() {
            Message message = Message.fromCQ("Hi[CQ:face,id=1]there[CQ:face,id=2]!");
            
            assertNotNull(message);
            assertEquals("Hi[CQ:face,id=1]there[CQ:face,id=2]!", message.toCQ());
        }

        @Test
        @DisplayName("should handle text with special characters")
        void testHandleTextWithSpecialCharacters() {
            Message message = Message.fromCQ("Hello, [World] & Test");
            
            assertNotNull(message);
            assertEquals("Hello, &#91;World&#93; &amp; Test", message.toCQ());
        }

        @Test
        @DisplayName("should handle escaped characters in CQ codes")
        void testHandleEscapedCharactersInCQ() {
            Message message = Message.fromCQ("[CQ:face,id=test&#44;123]");
            
            assertNotNull(message);
            assertEquals("[CQ:face,id=test&#44;123]", message.toCQ());
        }

        @Test
        @DisplayName("should handle empty CQ string")
        void testHandleEmptyCQString() {
            Message message = Message.fromCQ("");
            
            assertNotNull(message);
            assertEquals("", message.toCQ());
        }

        @Test
        @DisplayName("should handle CQ code without parameters")
        void testHandleCQCodeWithoutParameters() {
            Exception ex = assertThrows(IllegalArgumentException.class, () -> Message.fromCQ("[CQ:face]"));
            assertEquals("Face data requires at least one argument: id", ex.getMessage());
        }

        @Test
        @DisplayName("should handle unknown CQ codes")
        void testHandleUnknownCQCodes() {
            Message message = Message.fromCQ("Hello[CQ:unknown,type=test]World");
            
            assertNotNull(message);
            assertEquals("HelloWorld", message.toCQ()); // Unknown codes return empty string
        }

        @Test
        @DisplayName("should handle malformed CQ codes")
        void testHandleMalformedCQCodes() {
            Message message = Message.fromCQ("Hello[CQ:malformedWorld");
            
            assertNotNull(message);
            // Should handle gracefully
        }

        @Test
        @DisplayName("should throw when image missing file")
        void testImageMissingFile() {
            Exception ex = assertThrows(IllegalArgumentException.class,
                    () -> Message.fromCQ("[CQ:image,summary=test]"));

            assertEquals("Send side image data requires at least one argument: file", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Text Escaping Tests")
    class TextEscapingTests {

        @Test
        @DisplayName("should escape special characters")
        void testEscapeSpecialCharacters() {
            // Test through CQ generation
            Message message = Message.builder()
                    .text("Hello, [World] & Test")
                    .build();
            
            assertEquals("Hello, &#91;World&#93; &amp; Test", message.toCQ());
        }

        @Test
        @DisplayName("should escape commas in face ids")
        void testEscapeCommasInFaceIds() {
            Message message = Message.builder()
                    .face("test,123")
                    .build();
            
            assertEquals("[CQ:face,id=test&#44;123]", message.toCQ());
        }

        @Test
        @DisplayName("should unescape special characters")
        void testUnescapeSpecialCharacters() {
            String escaped = "Hello&#44; &#91;World&#93; &amp; Test";
            String unescaped = Message.unescape(escaped);
            assertEquals("Hello, [World] & Test", unescaped);
        }

        @Test
        @DisplayName("should handle empty string un-escaping")
        void testHandleEmptyStringEscaping() {
            assertEquals("", Message.unescape(""));
        }

        @Test
        @DisplayName("should handle null-like strings")
        void testHandleNullLikeStrings() {
            assertEquals("null", Message.unescape("null"));
        }

        @Test
        @DisplayName("should handle mixed special characters")
        void testMixedSpecialCharacters() {
            String input = "Test &amp; &#91;comma&#44;] &";
            String result = Message.unescape(input);
            assertEquals("Test & [comma,] &", result);
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("should round-trip JSON serialization")
        void testRoundTripJsonSerialization() {
            Message original = Message.builder()
                    .text("Hello")
                    .face("123")
                    .text("World")
                    .build();
            
            String json = original.toJson();
            Message deserialized = Message.fromJson(json);
            
            assertEquals(original.toCQ(), deserialized.toCQ());
        }

        @Test

        @DisplayName("should round-trip CQ parsing")
        void testRoundTripCQParsing() {
            String cqString = "Hello[CQ:face,id=100]World[CQ:face,id=200]!";
            Message message = Message.fromCQ(cqString);
            String regenerated = message.toCQ();
            
            assertEquals("Hello[CQ:face,id=100]World[CQ:face,id=200]!", regenerated);
        }

        @Test
        @DisplayName("should handle large messages")
        void testHandleLargeMessages() {
            StringBuilder largeText = new StringBuilder();
            Message.Builder builder = Message.builder();
            
            for (int i = 0; i < 100; i++) {
                largeText.append("Text").append(i).append(" ");
                builder.text("Text" + i + " ");
            }
            
            Message message = builder.build();
            assertNotNull(message);
            assertEquals(largeText.toString(), message.toCQ());
            assertTrue(message.toJson().length() > 1000);
        }

        @Test
        @DisplayName("should handle unicode characters")
        void testHandleUnicodeCharacters() {
            Message message = Message.builder()
                    .text("你好世界 🌍")
                    .face("微笑")
                    .build();
            
            assertNotNull(message);
            assertEquals("你好世界 🌍[CQ:face,id=微笑]", message.toCQ());
        }

        @Test
        @DisplayName("should round-trip complex mixed types")
        void testRoundTripComplexTypes() {
            String cqString = "Hello[CQ:at,qq=123][CQ:json,data={\"action\":\"test\"}]";
            Message message = Message.fromCQ(cqString);

            assertEquals(cqString, message.toCQ());
            assertTrue(message.toJson().contains("\"type\":\"at\""));
            assertTrue(message.toJson().contains("\"type\":\"json\""));
        }

        @Test
        @DisplayName("should handle 1000 messages efficiently")
        void testLargeMessagePerformance() {
            Message.Builder builder = Message.builder();
            for (int i = 0; i < 1000; i++) {
                builder.text("msg" + i).face(String.valueOf(i % 100));
            }

            assertTimeout(Duration.ofMillis(500), () -> {
                Message message = builder.build();
                assertFalse(message.toJson().isEmpty());
            });
        }
    }
}