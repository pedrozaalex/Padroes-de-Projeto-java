package Main.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Main.Model.Disciplina;

public class DisciplinaRepository {
    private List<Disciplina> disciplinas = new ArrayList<>();
    private String arquivoDisciplinas;

    public DisciplinaRepository(String arquivoDisciplinas) {
        this.arquivoDisciplinas = arquivoDisciplinas;
    }

    public void carregarDados() {
        disciplinas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoDisciplinas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(";");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String nome = parts[1].trim();
                    int docenteId = Integer.parseInt(parts[2].trim());
                    Disciplina disc = new Disciplina(id, nome, docenteId);
                    disciplinas.add(disc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Disciplina buscarPorId(int id) {
        for (Disciplina disc : disciplinas) {
            if (disc.getId() == id) {
                return disc;
            }
        }
        return null; // Retorna null se a disciplina não for encontrada
    }

    
    public void salvarOuAtualizarDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina inválida");
        }

        Disciplina existente = buscarPorId(disciplina.getId());
        if (existente != null) {
            // Atualizar disciplina existente
            existente.put("nome", disciplina.getNome());
            existente.put("docenteResponsavelId", disciplina.getDocenteResponsavelId());
            existente.put("descricao", disciplina.getDescricao());
            existente.put("horario", disciplina.getHorario());
            existente.put("capacidadeMaxima", disciplina.getCapacidadeMaxima());
        } else {
            // Adicionar nova disciplina
            disciplinas.add(disciplina);
        }
    }

}