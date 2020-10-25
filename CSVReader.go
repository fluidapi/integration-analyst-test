package main

import (
	"bufio"
	"encoding/csv"
	"encoding/json"
	"fmt"
	"io"
	"os"
	"sort"
	"strconv"
	"time"
)

type COVID struct {
	COVIDMonth  string `json:"month"`
	COVIDCases  int    `json:"cases"`
	COVIDDeaths int    `json:"deaths"`
}

func AbrirAqruivo(arquivoStr string) *os.File {
	// ABRIR O ARQUIVO CSV
	csvFile, _ := os.Open(arquivoStr)

	return csvFile
}

func LerRegistros(csvFile *os.File) [][]string {
	// PEGA PRIMEIRA LINHA DO ARQUIVO
	primeiraLinha, _ := bufio.NewReader(csvFile).ReadSlice('\n')

	// PULA PRIMEIRA LINHA DO ARQUIVO
	_, _ = csvFile.Seek(int64(len(primeiraLinha)), io.SeekStart)

	// LER OS REGISTROS
	r := csv.NewReader(csvFile)
	registros, _ := r.ReadAll()

	return registros
}

func FormatarData(dataStr string) string {
	// PEGA A DATA FORMATADA SOMENTE ANO-MES (YYYY-MM)
	data, _ := time.Parse("2006-01-02", dataStr)

	return fmt.Sprintf("%d-%02d", data.Year(), data.Month())
}

func ProcurarIndiceMes(covid []COVID, dataFormatada string) (int, int) {
	// QUANTIDADE REGISTROS JÁ INCLUIDOS
	qtdRegistros := len(covid)

	// PROCURA SE A DATA JÁ FOI INCLUIDA
	idx := sort.Search(qtdRegistros, func(j int) bool {
		return string(covid[j].COVIDMonth) >= dataFormatada
	})

	return idx, qtdRegistros
}

func ProcessarRegistros(registros [][]string) []COVID {
	// CRIA AS VARIAVEIS DO TIPO COVID
	var covid []COVID

	// LOOP POR TODOS OS REGISTROS DO ARQUIVO CSV
	for _, reg := range registros {
		dataFormatada := FormatarData(reg[0])

		// BUSCA A DATA FORMATADA DENTRO DOS REGISTROS JÁ INCLUIDOS
		// SE ENCONTRAR O REGISTRO, RETORNA O INDEX PARA ACUMULAR OS VALORES
		idxRegistro, qtdRegistros := ProcurarIndiceMes(covid, dataFormatada)

		// INCLUIR UM REGISTRO NOVO SE:
		// 		NÃO HOUVER REGISTROS INCLUIDOS, INCLUIR UM NOVO, OU
		// 		A QUANTIDADE DE REGISTROS FOR IGUAL AO INDEX RETORNADO, OU
		// 		A DATA FORMATADA NÃO FOR ENCONTRADA NOS REGISTROS INCLUIDOS
		incluirNovo := qtdRegistros == 0 || qtdRegistros == idxRegistro || covid[idxRegistro].COVIDMonth != dataFormatada

		if incluirNovo {
			// INCLUIR UM NOVO REGISTRO
			covid = IncluirRegistro(covid, dataFormatada, reg[1], reg[2])
		} else {
			// ACUMULAR OS VALORES EM UM REGISTRO JÁ ENCONTRADO
			AtualizarRegistro(covid, reg[1], reg[2], idxRegistro)
		}

	}

	return covid
}

func IncluirRegistro(covid []COVID, data, casos, mortes string) []COVID {
	var cov COVID

	cov.COVIDMonth = data
	cov.COVIDCases, _ = strconv.Atoi(casos)
	cov.COVIDDeaths, _ = strconv.Atoi(mortes)

	// INCLUIR NOVO REGISTRO
	return append(covid, cov)
}

func AtualizarRegistro(covid []COVID, casos, mortes string, idx int) {
	casosInt, _ := strconv.Atoi(casos)
	mortesInt, _ := strconv.Atoi(mortes)

	covid[idx].COVIDCases += casosInt
	covid[idx].COVIDDeaths += mortesInt
}

func ConverterDadosParaJSON(covid []COVID) []byte {
	// CONVERTER DADOS PARA JSON FORMATADO
	json_data, _ := json.MarshalIndent(covid, "", "  ")

	return json_data
}

func GerarArquivoJSON(outputStr string, json_data []byte) {
	// GERAR ARQUIVO JSON
	json_file, _ := os.Create(outputStr)
	defer json_file.Close()

	json_file.Write(json_data)
	json_file.Close()
}

func main() {
	csvFile := AbrirAqruivo("input.csv")

	registros := LerRegistros(csvFile)

	covid := ProcessarRegistros(registros)

	json_data := ConverterDadosParaJSON(covid)

	GerarArquivoJSON("output.json", json_data)
}
