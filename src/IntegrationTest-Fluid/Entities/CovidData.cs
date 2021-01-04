using CsvHelper.Configuration.Attributes;
using System;

namespace IntegrationTest_Fluid.Data
{
    public class CovidData
    {
        [Name("date")]
        public DateTime Date { get; set; }
        [Name("cases")]
        public int Cases { get; set; }
        [Name("deaths")]
        public int Deaths { get; set; }
        [Name("uf")]
        public string Uf { get; set; }
    }
}
