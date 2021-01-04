using CsvHelper;
using IntegrationTest_Fluid.Interfaces;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;

namespace IntegrationTest_Fluid.Infraestructure
{
    public class CsvContext : ICsvContext
    {
        public IEnumerable<TResult> GetCsvData<TResult>(string file)
        {
            if (File.Exists(file))
            {
                using var reader = new StreamReader(file);
                using var csv = new CsvReader(reader, CultureInfo.InvariantCulture);
                return csv.GetRecords<TResult>().ToList();
            }
            else
            {
                throw new FileNotFoundException(file);
            }
        }
    }
}
