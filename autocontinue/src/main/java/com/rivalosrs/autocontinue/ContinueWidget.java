package com.rivalosrs.autocontinue;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import net.runelite.api.widgets.WidgetInfo;

@Value
@Data
public class ContinueWidget {

  private final int groupId;
  private final int fileId;
  private final int childId;
  private final WidgetInfo widgetInfo;
  private Type type;

  public ContinueWidget(int groupId, int childId) {
    this.groupId = groupId;
    this.fileId = -1;
    this.childId = childId;
    this.widgetInfo = null;
    this.type = Type.CHILD;
  }

  public ContinueWidget(WidgetInfo widgetInfo) {
    this.groupId = -1;
    this.fileId = -1;
    this.childId = -1;
    this.widgetInfo = widgetInfo;
    this.type = Type.WIDGET_INFO;
  }

  public ContinueWidget(int groupId, int fileId, int childId) {
    // Not sure if `fileId` is ever used. The use of dynamic children in widgets is taken from
    // Illumine's iUtils
    this.groupId = groupId;
    this.fileId = fileId;
    this.childId = childId;
    this.widgetInfo = null;
    this.type = Type.DYNAMIC_CHILD;
  }

  @NoArgsConstructor
  public enum Type {
    WIDGET_INFO,
    CHILD,
    DYNAMIC_CHILD;
  }
}
