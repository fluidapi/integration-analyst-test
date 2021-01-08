using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;

namespace IntegrationTest_Fluid
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>()
                        .UseUrls(urls: "http://*:5000");
                });
            // Host.CreateDefaultBuilder(args)
            //     .UseStartup<Startup>()
            //     // params string[] urls
            //     .UseUrls(urls: "http://*:5000")
            //     .Build();
    }
}
