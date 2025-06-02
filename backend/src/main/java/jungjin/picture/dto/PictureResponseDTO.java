package jungjin.picture.dto;

import jungjin.picture.domain.Picture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureResponseDTO {
    private Long id;
    private String name;
    private String etc;
    @Builder.Default
    private String status = "S1";
    private LocalDateTime createDate;
    private List<PictureFileDTO> files;
    private List<PictureFileDTO> adminFiles;
    private String userName;
    private String userPhone;
    private String userEmail;

    public static PictureResponseDTO fromPicture(Picture picture) {
        List<PictureFileDTO> fileDTOs = Optional.ofNullable(picture.getFileList())
                .orElse(Collections.emptyList())
                .stream()
                .map(PictureFileDTO::from)
                .collect(Collectors.toList());

        List<PictureFileDTO> adminFileDTOs = Optional.ofNullable(picture.getAdminFileList())
                .orElse(Collections.emptyList())
                .stream()
                .map(PictureFileDTO::from)
                .collect(Collectors.toList());

        return PictureResponseDTO.builder()
                .id(picture.getId())
                .userName(picture.getUser().getName())
                .userPhone(picture.getUser().getPhone())
                .userEmail(picture.getUser().getEmail())
                .name(picture.getName())
                .etc(picture.getEtc())
                .status(picture.getStatus())
                .createDate(picture.getCreateDate())
                .files(fileDTOs)
                .adminFiles(adminFileDTOs)
                .build();
    }
}
