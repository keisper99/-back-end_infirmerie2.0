package org.institutsaintjean.INFIRMERIE_IUSJC.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class EmailMessagePieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMAIL_PIECE_JOINTE")
    private Long idEmailPieceJointe;

    private String content;
    private String subject;
    private File pieceJointe;
    private String emailEtudiant;
    private String emailContactUrgence;
}
