package com.example.joguinhonovo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.joguinhonovo.databinding.ActivityMainBinding

// Definição da classe MainActivity que herda de AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Variável de ligação para acessar os elementos da interface do usuário
    private lateinit var binding: ActivityMainBinding

    // Lista de palavras possíveis para o jogo
    private val palavras = listOf("rosa", "Willoughby", "Jupiter", "marshmallow","livros")

    // Seleciona uma palavra aleatória da lista e a converte para maiúsculas
    private val palavraSelecionada = palavras.random().uppercase()

    // Cria um array de caracteres para representar a palavra oculta, inicialmente preenchida com '_'
    private val palavraOculta = CharArray(palavraSelecionada.length) { '_' }

    // Variável para contar o número de tentativas restantes
    private var tentativas = 6

    // Lista para armazenar as letras que já foram adivinhadas
    private val letrasAdivinhadas = mutableListOf<Char>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Infla o layout usando o binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Atualiza a tela inicial com os valores padrão
        atualizarTela()

        // Define o comportamento do botão de adivinhar quando é clicado
        binding.adivinharb.setOnClickListener {
            // Obtém a entrada do usuário e a converte para maiúsculas
            val entrada = binding.letraentraText.text.toString().uppercase()

            // Verifica se a entrada não está vazia
            if (entrada.isNotEmpty()) {

                // Pega a primeira letra da entrada
                val letra = entrada[0]

                // Verifica se a letra ainda não foi adivinhada
                if (!letrasAdivinhadas.contains(letra)) {

                    // Adiciona a letra à lista de letras adivinhadas
                    letrasAdivinhadas.add(letra)

                    // Verifica se a palavra selecionada contém a letra
                    if (palavraSelecionada.contains(letra)) {

                        // Atualiza o array da palavra oculta com a letra correta nas posições certas
                        for (i in palavraSelecionada.indices) {
                            if (palavraSelecionada[i] == letra) {
                                palavraOculta[i] = letra
                            }
                        }
                    } else {
                        // Se a letra não estiver na palavra, decrementa o número de tentativas
                        tentativas--
                    }
                    // Limpa o campo de entrada
                    binding.letraentraText.text.clear()
                    // Atualiza a tela com os novos valores
                    atualizarTela()
                }
            }
        }
    }

    private fun atualizarTela() {
        // Exibe a palavra oculta com as letras adivinhadas
        binding.palavraOculta.text = palavraOculta.joinToString(" ")

        // Exibe o número de tentativas restantes
        binding.tentativasText.text = "Restam: $tentativas"

        // Verifica se o jogador ganhou ou perdeu e atualiza o status
        if (!palavraOculta.contains('_')) {
            binding.statustext.text = "Parabéns! $palavraSelecionada"
            binding.adivinharb.isEnabled = false
        } else if (tentativas <= 0) {
            binding.statustext.text = "Perdeu! Era $palavraSelecionada"
            binding.adivinharb.isEnabled = false
        }
    }
}