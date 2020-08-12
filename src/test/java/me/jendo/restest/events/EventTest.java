package me.jendo.restest.events;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn SpringREST API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        // Given
        String name = "Event";
        String description = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @ParameterizedTest(name = "{index} => basePrice={0}, maxPrice={1}, isFree={2}")
    /*
    @CsvSource({
            "0, 0, true",
            "100, 0, false",
            "0, 1000, false"
    })*/
    //@MethodSource("testFreeParams")
    @MethodSource("testFreeParamsProvider")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }
    private static Object[] testFreeParams() {
        return new Object[]{
                new Object[]{0, 0, true},
                new Object[]{100, 0, false},
                new Object[]{0, 100, false}
        };
    }
    private static Stream<Arguments> testFreeParamsProvider() {
        return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(100, 0, false),
                Arguments.of(0, 100, false)
        );
    }

    @Test
    public void testOffline() {
        // Given
        Event event = Event.builder()
                .location("강남역 네이버 D2 스타텁 팩토리")
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isTrue();

        // Given
        event = Event.builder()
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isFalse();
    }

}