# Requires data.table package. Install it with install.packages("data.table")
library(data.table)
library(stringr)
library(ggplot2)
library(cowplot)

# --- Options
pdf(width = 8.5, height = 11)

# File names postfixes, to define test groups
testGroupsRegexes <- c("\\.high", "\\.low") #, "")
testGroupsOutputNames <- c("high", "low") #, "all")
testGroupsNames <- c("High-End", "Low-End") #, "All")

# Use harmonic average to build chart. If false, will use
# normalized values
useHarmonicAverageInChart <- FALSE

# Set to true to save calculations to a CSV file
saveCSV <- FALSE

# Used to filter output. Shows only single task
# Put to false to disable it, otherwise set it ex. to "blur5"
filterTask <- FALSE #"Multiple"

# Plot aside
plotAside <- FALSE

# ---

# Used to extract first word of Tag. Will be called taskName later
# It is assumed that Tags start with the same word, and precise description
# comes later. Example:
#
# RGBAtoGRAY - (pointers|rsSet)
# RGBAtoGRAY - FilterScript
# RGBAtoGRAY
# RGBAtoGRAY - (pointers)
#
# All previous rows will produce RGBAtoGRAY taskName
getFirstWord <- function(x) {
  str_match(x[1], "^(\\w+)")[, 1]
}

plots <- list()

for (groupIdx in 1:length(testGroupsRegexes)) {
  
  # Clear environment
  rm(
    countVector,
    means,
    variances,
    minValues,
    maxValues,
    globalTasks,
    maxValueForTask,
    minValueForTask,
    normalizedMeans,
    profilerData,
    dt,
    sortedAvgTable,
    df
  )
  
  group = testGroupsRegexes[groupIdx]
  groupOutputName = testGroupsOutputNames[groupIdx]
  
  sets <-
    list.files(
      path = "crowd",
      full.names = TRUE,
      pattern = paste(group, "\\.csv", sep = "")
    )
  
  # Output file
  outputFile <- paste("output-", groupOutputName, ".csv", sep = "")
  
  # Clean outputFile
  if(saveCSV == TRUE)
  unlink(outputFile)
  
  resultsPairList <- list()
  
  # For every set, perform initial calculations
  for (setIdx in 1:length(sets)) {
    dataSetFileName <- sets[setIdx]
    # Load data from file and converts it to a table
    profilerData <-
      read.csv(dataSetFileName, head = TRUE, sep = ",")
    dt <- data.table(profilerData, stringsAsFactors = FALSE)
    
    # Reference: http://stats.stackexchange.com/questions/8225/how-to-summarize-data-by-group-in-r
    # Groups data using tags, calculating their mean, harmonic mean and relative variance.
    # Then, sorts by taskName and by harmonic mean
    sortedAvgTable <- dt[, list(
      taskName = getFirstWord(Tag),
      harmonicMean = 1 / mean(1 / Timing),
      relVariance = var(Timing) / mean(Timing)
    ), by = Tag][order(taskName, harmonicMean), ]
    # Group by tag and sorts table elements by taskName and mean
    
    # If filter exists, filter out tasks whose taskName does not belong
    # to filter
    if (filterTask != FALSE){
      sortedAvgTable <- sortedAvgTable[ taskName == filterTask ]
    }
    
    # Adds a percentage colum, used to display how worse a kernel function performs when compared
    # to the best one of the same task
    getPercentage <- function (taskName, currentMean) {
      indexOfFirstElementOfTask <-
        grep(taskName, sortedAvgTable[, taskName])[1]
      firstElementOfTaskRow <-
        sortedAvgTable[indexOfFirstElementOfTask,]
      returnValue <-
        (currentMean / firstElementOfTaskRow$harmonicMean)
      
      return(returnValue)
    }
    
    # Gets the normalized value for measurement. It calculates a value ranging
    # from 0 to 100, where the nearer to 0, the faster was the kernel to execute,
    # inside the same task.
    getNormalizedValue <- function (taskName, currentMean) {
      indexOfFirstElementOfTask <-
        grep(taskName, sortedAvgTable[, taskName])[1]
      indexOfLastElementOfTask <-
        tail(grep(taskName, sortedAvgTable[, taskName]), n = 1)
      firstElementOfTaskRow <-
        sortedAvgTable[indexOfFirstElementOfTask,]
      lastElementOfTaskRow <-
        sortedAvgTable[indexOfLastElementOfTask,]
      
      returnValue = (currentMean - firstElementOfTaskRow$harmonicMean) /
        (lastElementOfTaskRow$harmonicMean - firstElementOfTaskRow$harmonicMean)
      
      return(returnValue * 100)
    }
    
    # Add normalized values, and sorts by task name and mean
    sortedAvgTable[, normalizedValue := getNormalizedValue(taskName, harmonicMean), by =
                     "taskName,harmonicMean"][order(taskName, harmonicMean), ]
    
    for (rowIdx in 1:nrow(sortedAvgTable))
    {
      row <- sortedAvgTable[rowIdx,]
      currentTag <- as.character(row$Tag)
      
      if (is.null(resultsPairList[[currentTag]]))
      {
        if (useHarmonicAverageInChart) {
          resultsPairList[[currentTag]] <-
            c(row$harmonicMean)
        } else {
          resultsPairList[[currentTag]] <-
            c(row$normalizedValue)
        }
      }
      else
      {
        if (useHarmonicAverageInChart) {
          resultsPairList[[currentTag]] <-
            c(resultsPairList[[currentTag]], row$harmonicMean)
        } else {
          resultsPairList[[currentTag]] <-
            c(resultsPairList[[currentTag]], row$normalizedValue)
        }
      }
    }
    
    if(saveCSV){
    # Write results to a file that can be opened directly using Microsoft Excel
    write(paste0("Dataset: ", dataSetFileName),
          file = outputFile,
          append = TRUE)
    write(paste0("Total samples: ", nrow(profilerData)),
          file = outputFile,
          append = TRUE)
    write.table(
      sortedAvgTable,
      file = outputFile,
      quote = FALSE,
      sep = ",",
      row.names = FALSE,
      col.names = TRUE,
      append = TRUE
    )
    }
  }
  
  # Expand result list
  count <- 0
  countVector <- c()
  means <- c()
  variances <- c()
  minValues <- c()
  maxValues <- c()
  globalTasks <- c()
  
  maxValuesForTasksList <- list()
  minValuesForTasksList <- list()
  
  # Objective is to build a bar plot with error
  for (tag in names(resultsPairList)) {
    currentTagValues <- resultsPairList[[tag]]
    task <- getFirstWord(tag)
    globalTasks <- c(globalTasks, task)
    
    currentMean <- mean(currentTagValues)
    means <- c(means, currentMean)
    
    currentVariance <-
      var(currentTagValues) / mean(currentTagValues)
    variances <- c(variances, currentVariance)
    
    minValue <- min(currentTagValues)
    maxValue <- max(currentTagValues)
    
    minValues <- c(minValues, minValue)
    maxValues <- c(maxValues, maxValue)
    
    if (is.null(maxValuesForTasksList[[task]])) {
      maxValuesForTasksList[[task]] <- maxValue
      minValuesForTasksList[[task]] <- minValue
    }
    else
    {
      maxValuesForTasksList[[task]] <-
        max(maxValuesForTasksList[[task]], maxValue)
      minValuesForTasksList[[task]] <-
        min(minValuesForTasksList[[task]], minValue)
    }
    
    countVector <- c(countVector, count)
    count <- count + 1
  }
  
  normalizedMeans <- array(dim = length(means))
  
  # For each task, normalize values of tests
  for (index in 1:length(means)) {
    task <- getFirstWord(names(resultsPairList)[index])
    
    minValueForTask <- minValuesForTasksList[[task]]
    maxValueForTask <- maxValuesForTasksList[[task]]
    
    referenceMinValue = 0
    
    diff <- maxValueForTask - referenceMinValue
    
    normalizedMeans[index] <-
      (means[index] - referenceMinValue) / diff * 100
    
    minValues[index] <-
      (minValues[index] - referenceMinValue) / diff * 100
    maxValues[index] <-
      (maxValues[index] - referenceMinValue) / diff * 100
    
  }
  
  df = data.frame(tags = names(resultsPairList),
                  means,
                  normalizedMeans,
                  minValues,
                  maxValues,
                  countVector)
  
  if (useHarmonicAverageInChart) {
    minPlotX <- 0
    maxPlotX <- 100
    axisLabel <- "Harmonic average (%)"
  } else {
    minPlotX <- -10
    maxPlotX <- 110
    axisLabel <- "Normalized averages (%)"
  }
  
  # --- Create chart
  
  # Main draw process
  plot <- ggplot(df, aes(
    x = tags, y = normalizedMeans, fill = globalTasks
  ))
  
  # Print legend only if not filtered
  if (filterTask == FALSE){
    plot <- plot + labs(fill = "Tasks",
                        x = NULL,
                        y = axisLabel,title=testGroupsNames[groupIdx]) +
      guides(fill = guide_legend(reverse = TRUE))
  }
  else
  {
    plot <- plot + labs(fill = FALSE,
                        x = NULL,
                        y = axisLabel,title=testGroupsNames[groupIdx]) + 
      guides(fill = FALSE)
  }
  
  if(plotAside == TRUE){
    # sequent groups should have no axis text
    if(groupIdx == 1){
      plot <- plot + theme(axis.text.y = element_text(colour = "#000000", face = "bold"))
    }
    else
    {
      plot <- plot + theme(axis.text.y=element_blank(),axis.title.y=element_blank())
    }
  } else
  {
    plot <- plot + theme(axis.text.y = element_text(colour = "#000000", face = "bold"))
  }
  
  plot <- plot +
    geom_bar(position = position_dodge(), stat = "identity") +
    geom_errorbar(
      aes(ymin = minValues, ymax = maxValues),
      width = .5,
      # Width of the error bars
      position = position_dodge(2),
      colour = "#000000"
    )  +
    geom_label(
      aes(label = round(means, 2)),
      size = 3.5,
      hjust = 0.5,
      vjust = "center",
      show.legend = FALSE
    ) + scale_y_continuous(expand = c(0, 0)) +
    coord_flip(ylim = c(minPlotX, maxPlotX))
  
  plots[[group]] <- plot

}

if(plotAside == TRUE){
  do.call("plot_grid",c(as.list(plots),list(rel_widths=c(1.5, 1))))
}else{
  groupsLen <- length(testGroupsRegexes)
  for(i in 1:groupsLen)
  {
    idx = as.integer(i)
    p <- plots[idx]
    print(p)
  }
}