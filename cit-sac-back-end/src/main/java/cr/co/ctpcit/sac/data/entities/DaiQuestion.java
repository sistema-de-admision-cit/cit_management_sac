package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daiquestions")
public class DaiQuestion {
    @Id
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "image_url")
    private String imageUrl;

}