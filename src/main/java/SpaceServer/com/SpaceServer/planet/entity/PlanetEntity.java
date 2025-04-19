package SpaceServer.com.SpaceServer.planet.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planet")
@Getter
@NoArgsConstructor
public class PlanetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planetId;

    @Column(name = "name", nullable = false)
    private String name; // 행성 이름

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 행성 이미지 URL

    @Column(name = "size", nullable = false)
    private Double size; // 행성 크기

    @Column(name = "mass", nullable = false)
    private Double mass; // 질량

    @Column(name = "distance_from_sun", nullable = false)
    private Double distanceFromSun; // 태양과의 거리

    @Column(name = "surface_temperature", nullable = false)
    private Double surfaceTemperature; // 표면 온도

    @Column(name = "rotation", nullable = false)
    private String rotation; // 자전

    @Column(name = "axial_tilt", nullable = false)
    private String axialTilt; // 자전축

    @Column(name = "revolution", nullable = false)
    private String revolution; // 공전

    @Column(name = "satellite_1")
    private String satellite1; // 위성 1

    @Column(name = "satellite_2")
    private String satellite2; // 위성 2

    @Column(name = "satellite_3")
    private String satellite3; // 위성 3

    @Column(name = "satellite_4")
    private String satellite4; // 위성 4

    @Column(name = "satellite_5")
    private String satellite5; // 위성 5 (nullable)

    @Builder
    public PlanetEntity(String name, String imageUrl, Double size, Double mass,
                        Double distanceFromSun, Double surfaceTemperature,
                        String rotation, String axialTilt, String revolution,
                        String satellite1, String satellite2, String satellite3,
                        String satellite4, String satellite5) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.size = size;
        this.mass = mass;
        this.distanceFromSun = distanceFromSun;
        this.surfaceTemperature = surfaceTemperature;
        this.rotation = rotation;
        this.axialTilt = axialTilt;
        this.revolution = revolution;
        this.satellite1 = satellite1;
        this.satellite2 = satellite2;
        this.satellite3 = satellite3;
        this.satellite4 = satellite4;
        this.satellite5 = satellite5;
    }
}
